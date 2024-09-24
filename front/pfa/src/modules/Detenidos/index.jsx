import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Button, Input, InputNumber, Form, message, Modal, Table, Select, Spin } from "antd"
import serv from "../../services/serv"
import Todos from  "../../components/Todos"

const layoutNuevo = { labelCol: {span: 8} , wrapperCol: {span: 16}}

function Detenidos () {       
    const [detenidos, setDetenidos] = useState([]);
    const [bandas, setBandas] = useState([]);    
    const [bandaNueva, setBandaNueva] = useState(null);
    // Comunes entre entidades
    const [entidadM, setEntidadM] = useState(null);
    const [mostrarDatos, setMostrarDatos] = useState(false);    
    const [modif, setModif] = useState(false);
    const [modalN, setModalN] = useState(false);
    const [modalM, setModalM] = useState(false);
    const [modalB, setModalB] = useState(false);
    const [cargando, setCargando] = useState(false);    
    const { modo } = useParams();
    const [formNuevo] = Form.useForm();
    const [formModif] = Form.useForm();
    const [formBorra] = Form.useForm();
    
    async function guardarID (x) {
        const y = JSON.parse(x)
        if (y.banda) {
            try {
                const r = await serv.leer("b", y.banda);
                y.numeroBanda = r.numero;
                y.nombreBanda = r.nombre;
            }
            catch (err) {
                console.log(err);
            }
        }
        setEntidadM(y);
        setMostrarDatos(true)
    }    

    function guardarBanda (x) {
        const y = JSON.parse(x);
        setBandaNueva(y);
    }

    async function handleNuevo (x) {
        try {
            await serv.crear("d", x);
            message.success(
                <p>
                    ¡Alta exitosa! <br/>
                    Codigo: <b>{x.codigo}</b> <br/>
                    Nombre: <b>{x.nombre}</b> <br/>
                    Banda: <b>{x.banda || "No asignada"}</b> <br />
                </p>
            );
            formNuevo.resetFields();
            setModalN(false);
            pegar();
        }
        catch(err) {
            message.error(err.message);
        }
    }

    async function handleModif (v) {
        if (v.banda !== undefined) {
            const a = JSON.parse(v.banda);
            v.banda = a.id;
        }
        try {
            v.id = entidadM.id;
            await serv.actualizar("d", v.id, v);
            message.success("Cambios guardados.");
            pegar();
            formBorra.resetFields();
            formModif.resetFields();
            setModif(false);
            setModalB(false);
            setModalM(false);
            setMostrarDatos(false);
            setEntidadM(null);
        }
        catch (err) {
            message.error(err.message);
            console.log(err);
        }
    }

    async function handleBorra (x) {
        try {
            await serv.borrar("d", entidadM.id);
            message.success(
                <p>
                    ¡Baja exitosa! <br />
                    Codigo: <b>{entidadM.codigo}</b> <br/>
                    Nombre: <b>{entidadM.nombre}</b> <br/>
                    Banda: <b>{entidadM.banda || "ninguna."}</b> <br />
                </p>
            );
            pegar();
            formBorra.resetFields();
            setModif(false);
            setModalB(false);
            setMostrarDatos(false);
            setEntidadM(null);
        }
        catch (err) {
            message.error(err);
        }
    }

    function comprobarCambios () {       
        if (
            (!formModif.getFieldValue("codigo") || (entidadM.codigo === formModif.getFieldValue("codigo")))
            &&
            (!formModif.getFieldValue("nombre") || (entidadM.nombre === formModif.getFieldValue("nombre")))           
            &&
            (!formModif.getFieldValue("banda") || (!entidadM.banda && bandaNueva.id === -1))
        ) {
            message.warning("No se detectan cambios.");
        }
        else {
            setModalM(true);
        }
    }

    async function pegar () {    
        setCargando(true);
        try {        
            const resD = await serv.getAll("d");
            setDetenidos(resD);        
            console.log(detenidos);        
        }
        catch (err) {
            setDetenidos([]);
            console.log(err)
        }
        try {
            const resB = await serv.getAll("b");
            setBandas(resB)
            console.log(bandas);
        }
        catch (err) {
            setBandas([]);
            console.log(err);
        }
        setCargando(false);           
    }

    useEffect(() => {
        pegar();
    }, []);

    return (
        <>
            {!modo &&
                <> 
                    {cargando? 
                        <Spin size = "large" />
                        :
                        <Todos datos = {detenidos} entidad = "detenidos" />
                    }
                </>
            }
            {modo === "nuevo" &&
                <>
                    <h1>Nuevo detenido </h1>
                    <Form {...layoutNuevo}
                        form = {formNuevo}
                        name = "formNuevoDet"
                        onFinish = {handleNuevo}
                        style = {{maxWidth: 600}}
                    >
                        <Form.Item name = "codigo" 
                            label = {<b>Código</b>}
                            rules = {[{
                                required: true, 
                                message: "Código requerido"
                            }]}
                        >
                            <InputNumber 
                                placeholder = "Ingrese código"
                                style = {{width: 200}}
                            />
                        </Form.Item>
                        <Form.Item name = "nombre" 
                            label = {<b>Nombre</b>}
                            rules = {[{
                                required: true,
                                message: "Nombre requerido"
                            }]}
                        >
                            <Input
                                placeholder = "Ingrese nombre"
                                style = {{width: 200}}
                            />
                        </Form.Item>
                        <Form.Item name = "banda" 
                            label = {<b>Banda</b>}>
                            <Select
                                allowClear
                                showSearch
                                style = {{width: 200}}
                                placeholder = "Ingrese banda"    
                            >
                                {bandas.map(x => (
                                    <Select.Option key = {x.id} value = {x.id}>
                                        {x.numero + " - " + x.nombre}
                                    </Select.Option>                                    
                                ))
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item wrapperCol = {{offset: 8, span: 16}}>
                            <Button type = "primary" onClick={() => setModalN(true)}>
                                Enviar
                            </Button>
                            <Modal
                                keyboard = {false}
                                width = {300}    
                                closable = {false} maskClosable = {false}
                                open = {modalN}
                                title = "¿Confirmar alta?"
                                onCancel = {() => setModalN(false)}
                                okText = "Aceptar"
                                cancelText = "Cancelar"
                                onOk = {() => formNuevo.submit()}
                            >
                                Codigo: {formNuevo.getFieldValue("codigo")} <br />
                                Nombre: {formNuevo.getFieldValue("nombre")}
                            </Modal>
                        </Form.Item>
                    </Form>
                </>
            }
            {modo === "buscar" &&
                <>
                    <h1>Buscar detenido </h1>
                    <Form
                        form = {formBorra}
                        name = "formBorra"
                        onFinish = {handleBorra}
                    >
                        <Form.Item name = "a" label = {<b>Detenido</b>}>
                            <Select                                
                                showSearch
                                style = {{width: 300, height: 25}}
                                onChange = {guardarID}
                                placeholder = "Ingrese detenido"
                            >
                                {detenidos.map (x => (
                                    <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                        Codigo: <b>{x.codigo}</b>, nombre: <b>{x.nombre}</b>
                                    </Select.Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item name = "b">
                            <Modal
                                keyboard = {false}
                                width = {300}
                                closable = {false} maskClosable = {false}
                                open = {modalB}
                                title = "¿Confirma borrar detenido?"
                                okText = "Aceptar"
                                cancelText = "Cancelar"
                                onCancel = {() => setModalB(false)}
                                onOk = {() => formBorra.submit()}
                            >
                                {entidadM &&
                                    <>  
                                    Codigo: <b>{entidadM.codigo}</b> <br />
                                    Nombre: <b>{entidadM.nombre}</b> <br />                                    
                                    Banda: <b>{entidadM.nombreBanda || "no tiene."}</b>
                                </>
                                }
                                </Modal>
                            <Button danger 
                                disabled = {!mostrarDatos} 
                                style = {{width: 130}} 
                                type = "primary"
                                onClick = {() => setModalB(true)}
                            >
                                Borrar detenido
                            </Button>                            
                        </Form.Item>
                    </Form>
                    {mostrarDatos && 
                        <>
                            <h2>Datos del detenido</h2>
                            ID: <b>{entidadM.id}</b><br />
                            Codigo: <b>{entidadM.codigo}</b> <br />
                            Nombre: <b>{entidadM.nombre}</b> <br />
                            Banda: <b>{entidadM.nombreBanda || "no tiene"}</b> <br />
                            <br/>
                            <Button style = {{width: 130}}
                                type = "primary"
                                htmlType = "button"
                                onClick = {() => setModif(!modif)}
                            >
                                Modificar
                            </Button>
                        </>
                    }
                    {modif && 
                        <>
                            <Form
                                {...layoutNuevo}
                                form = {formModif}
                                name = "formModif"
                                onFinish = {handleModif}
                                style = {{maxWidth: 600}}
                            >
                                <Form.Item 
                                    name = "codigo"
                                    label = {<b>Codigo</b>}                                
                                >
                                    <InputNumber
                                        placeholder = "Ingrese nuevo código"
                                        style = {{width: 200}}
                                    />                                    
                                </Form.Item>
                                <Form.Item 
                                    name = "nombre"
                                    label = {<b>Nombre</b>} 
                                >
                                    <Input
                                        placeholder = "Ingrese nuevo nombre"
                                        style = {{width: 200}}
                                    />                                    
                                </Form.Item>
                                <Form.Item name = "banda" 
                                    label = {<b>Banda</b>}>
                                    <Select
                                        allowClear
                                        showSearch
                                        style = {{width: 200}}
                                        placeholder = "Ingrese banda"
                                        onChange = {guardarBanda}    
                                    >
                                        <Select.Option key = {-1} value = {JSON.stringify({id: -1, nombre: "sin banda"})}>
                                            <b>DESASOCIAR BANDA</b>
                                        </Select.Option>
                                        {bandas.map(x => (
                                            <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                                Nombre: <b>{x.nombre}</b>
                                            </Select.Option>                                    
                                        ))
                                        }                                        
                                    </Select>
                                </Form.Item>
                                <Form.Item wrapperCol = {{offset: 8, span: 16}}>
                                    <Button type = "primary" 
                                        onClick = {() => {                                            
                                            comprobarCambios();
                                        }}
                                    >
                                        Guardar cambios
                                    </Button>
                                    <Modal
                                        keyboard = {false}
                                        width = {500} 
                                        closable = {false} maskClosable = {false}
                                        open = {modalM}
                                        title = "¿Guardar cambios?"
                                        okText = "Aceptar"
                                        cancelText = "Cancelar"
                                        onCancel = {() => setModalM(false)}
                                        onOk = {() => formModif.submit()}
                                    >
                                        <>
                                            {formModif.getFieldValue("codigo") && 
                                                <>
                                                    Codigo actual: <b>{entidadM.codigo}</b> <br />
                                                    Codigo nuevo: <b>{formModif.getFieldValue("codigo")}</b> <br/>
                                                    <br/>
                                                </>
                                            }
                                            {formModif.getFieldValue("nombre") && 
                                                <>
                                                    Nombre actual: <b>{entidadM.nombre}</b> <br/>
                                                    Nombre nuevo: <b>{formModif.getFieldValue("nombre")}</b><br/>
                                                    <br/>
                                                </>
                                            }
                                            {formModif.getFieldValue("banda") && 
                                                <>
                                                    Entidad actual: <b>{entidadM.nombreBanda || "no tiene"}</b> <br/>
                                                    Entidad nuevo: <b>{bandaNueva.nombre}</b><br/>
                                                    <br/>
                                                </>
                                            }
                                        </>
                                    </Modal>
                                </Form.Item>
                            </Form>
                        </>
                    }
                </>
            }
        </>
    )
}

export default Detenidos