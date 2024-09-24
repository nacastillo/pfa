import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Button, Input, InputNumber, Form, message, Modal, Select, Spin } from "antd";
import serv from "../../services/serv"
import Todos from  "../../components/Todos";

const layoutNuevo = {labelCol: {span: 8}, wrapperCol: {span: 16}};

function Jueces() {       
    const [jueces, setJueces] = useState([]);
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

    const guardarID = async (x) => {
        const y = JSON.parse(x)        
        setEntidadM(y);
        setMostrarDatos(true);
    }

    const handleNuevo = async (v) => {
        try {
            (!v.aniosServicio) && (v.aniosServicio = 0);            
            await serv.crear("j", v);
            message.success(
                <p>
                    ¡Alta exitosa! <br />
                    <b>Clave:</b> {v.clave} <br />
                    <b>Nombre:</b> {v.nombre} <br />
                    <b>Años de servicio:</b> {v.aniosServicio}
                </p>
            )
            formNuevo.resetFields();
            setModalN(false);
            pegar();
        }
        catch (err) {
            message.error(err.message);
        }
    }

    const handleModif = async (v) => {
        try {
            v.id = entidadM.id;
            await serv.actualizar("j",v.id, v);
            message.success("Cambios guardados.");
            pegar();
            formBorra.resetFields();
            formModif.resetFields();
            setModif(false)
            setModalB(false);
            setModalM(false);
            setMostrarDatos(false);
            setEntidadM(null);
        }        
        catch (err) {
            message.error(err.message);
        }
    }

    const handleBorra = async (v) => {
        try {
            await serv.borrar("j",entidadM.id)
            message.success(
                <p>
                    ¡Baja exitosa! <br />
                    <b>Clave:</b> {entidadM.clave} <br />
                    <b>Nombre:</b> {entidadM.nombre} <br />
                    <b>Años de servicio:</b> {entidadM.aniosServicio}
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
            message.error(err.message);
        }
    }

    const comprobarCambios = () => {
        if (
            (!formModif.getFieldValue("clave") || (entidadM.clave === formModif.getFieldValue("clave")))
            &&
            (!formModif.getFieldValue("nombre") || (entidadM.nombre === formModif.getFieldValue("nombre")))
            &&
            (!formModif.getFieldValue("aniosServicio") || (entidadM.aniosServicio === formModif.getFieldValue("aniosServicio")))
        ) {
            message.warning("No se detectan cambios.");
        }
        else {
            setModalM(true);
        }
    }

    const pegar = async () => {
        setCargando(true);
        try {
            const res = await serv.getAll("j");
            setJueces(res);
        }
        catch (err) {
            setJueces([]);            
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
                    <Todos datos = {jueces} entidad = "jueces" />                    
                    }
                </>
            }
            {modo === "nuevo" &&
                <>
                    <h1>Nuevo juez </h1>
                    <Form {...layoutNuevo}
                        form = {formNuevo}
                        name = "formNuevoJuez"
                        onFinish = {handleNuevo}
                        style = {{maxWidth: 600}}                    
                    >
                        <Form.Item name = "clave" 
                            label = {<b>Clave</b>}                            
                            rules = {[{
                                required: true,
                                message: "Clave requerida"
                            }]}
                        >
                            <Input style = {{width: 200}} 
                                placeholder = "Ingrese clave"                                
                            />
                        </Form.Item>
                        <Form.Item name = "nombre"
                            label = {<b>Nombre</b>}                             
                            rules = {[{
                                required: true,
                                message: "Nombre requerido"
                            }]}
                        >
                            <Input style = {{width: 200}}
                                placeholder = "Ingrese nombre"
                            />
                        </Form.Item>
                        <Form.Item name = "aniosServicio"
                            label = {<b>Años en servicio</b>}                            
                        >
                            <InputNumber style = {{width: 200}}
                                placeholder = "Ingrese años de servicio"
                            />                            
                        </Form.Item>
                        <Form.Item wrapperCol = {{offset: 8, span: 16}}>
                            <Button type = "primary" onClick = {()=> setModalN(true)}>
                                Enviar
                            </Button>
                            <Modal width = {300}
                                keyboard = {false}
                                closable = {false} maskClosable = {false}
                                open = {modalN}
                                title = "¿Confirmar alta?"                                
                                okText = "Aceptar"
                                cancelText = "Cancelar"
                                onCancel = {() => setModalN(false)}
                                onOk = {() => formNuevo.submit()}
                            > 
                                <b>Clave:</b> {formNuevo.getFieldValue("clave")} <br />
                                <b>Nombre:</b> {formNuevo.getFieldValue("nombre")} <br />
                                <b>Años de servicio:</b> {formNuevo.getFieldValue("aniosServicio") || "0"} <br />
                            </Modal>
                            <Button onClick = {() => formNuevo.resetFields()}>
                                Borrar
                            </Button>
                        </Form.Item>
                    </Form>
                </>
            }
            {modo === "buscar" &&
                <>
                    <h1>Buscar juez </h1>
                    <Form
                        form = {formBorra}
                        name = "formBorra"
                        onFinish = {handleBorra}                       
                    >
                        <Form.Item name = "a" label = {<b>Juez</b>}>
                            <Select
                                showSearch
                                style = {{width: 400, height: 25}}
                                onChange = {guardarID}
                                placeholder = "Seleccione juez"
                            >
                                {
                                    jueces.map(x => (
                                        <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                            Clave: <b>{x.clave}</b>, nombre: <b>{x.nombre}</b>
                                        </Select.Option>
                                    ))
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item name = "modalB">
                            <Modal
                                keyboard = {false}
                                width = {300}
                                closable = {false} maskClosable = {false}
                                open = {modalB}
                                title = "¿Confirma borrar juez?"
                                okText = "Aceptar"
                                cancelText = "Cancelar"
                                onCancel = {() =>setModalB(false)}
                                onOk = {() => formBorra.submit()}
                            >
                                {entidadM &&                                 
                                    <>       
                                        <b>Clave:</b> {entidadM.clave} <br />
                                        <b>Nombre:</b> {entidadM.nombre} <br />
                                        <b>Años de servicio:</b> {entidadM.aniosServicio} <br />
                                    </>
                                }
                            </Modal>
                            <Button danger
                                disabled = {!mostrarDatos}
                                style = {{width: 110}}
                                type = "primary"
                                onClick = {() => setModalB(true)}
                            >
                                Borrar juez
                            </Button>
                        </Form.Item>
                    </Form>
                    {mostrarDatos && 
                        <>
                            <h2>Datos del juez</h2>
                            <b>ID: </b>{entidadM.id} <br/>
                            <b>Clave: </b>{entidadM.clave} <br/>
                            <b>Nombre: </b> {entidadM.nombre} <br/>
                            <b>Años de servicio: </b>{entidadM.aniosServicio} <br/> 
                            <b>Asaltos asignados: </b>{entidadM.asaltos.length} <br/>
                            <br/>
                            <Button type = "primary"
                                style = {{width: 110}}
                                htmlType = "button"
                                onClick= {() => setModif(!modif)}
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
                                    name = "clave"
                                    label = {<b>Clave</b>}
                                >
                                    <Input 
                                        placeholder = "Ingrese nueva clave interna" 
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
                                <Form.Item
                                    name = "aniosServicio"    
                                    label = {<b>Años de servicio</b>}
                                >
                                    <InputNumber
                                        placeholder = "Ingrese años de servicio"
                                        style = {{width: 200}}
                                    />
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
                                            {formModif.getFieldValue("clave") && 
                                                <>
                                                    Clave interna actual: <b>{entidadM.clave}</b> <br />
                                                    Clave interna nueva: <b>{formModif.getFieldValue("clave")}</b> <br/>
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
                                            {formModif.getFieldValue("aniosServicio") && 
                                                <>
                                                    Años de servicio actuales: <b>{entidadM.aniosServicio}</b> <br/>
                                                    Años de servicio nuevos: <b>{formModif.getFieldValue("aniosServicio")}</b> <br/>
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

export default Jueces