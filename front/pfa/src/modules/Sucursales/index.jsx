import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Button, Input, InputNumber, Form, message, Modal, Table, Select, Spin } from "antd"
import serv from "../../services/serv"
import Todos from  "../../components/Todos";

const layoutNuevo = {labelCol: {span: 8}, wrapperCol: {span: 16}};

function Sucursales() {
    const [sucursales, setSucursales] = useState([]);
    const [entidades, setEntidades] = useState([]);    
    const [bancoNuevo, setBancoNuevo] = useState(null);
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
        if (y.entidad) {
            try {
                const r = await serv.leer("e",y.entidad);
                y.nombreBanco = r.nombre;
                y.codigoBanco = r.codigo;
            }
            catch (err) {
                console.log(err);
            }
        }
        setEntidadM(y)
        setMostrarDatos(true);
    }

    const guardarBanco = (x) =>{
        const y = JSON.parse(x)
        setBancoNuevo(y);
    }

    const handleNuevo = async (v) => {
        try {
            (!v.cantidadEmpleados) && (v.cantidadEmpleados = 0);
            await serv.crear("s", v);
            message.success(
                <p>
                    ¡Alta exitosa! <br />
                    <b>Codigo:</b> {v.codigo} <br />
                    <b>Nombre:</b> {v.nombre} <br />
                    <b>Domicilio:</b> {v.domicilio} <br />
                    <b>Empleados:</b> {v.cantidadEmpleados} <br />
                    <b>ID de entidad asociada:</b> {v.entidad || "ninguna."}
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
        if (v.entidad !== undefined) {
            const a = JSON.parse(v.entidad);
            v.entidad = a.id;
        }
        try {
            v.id = entidadM.id;
            await serv.actualizar("s",v.id, v);
            message.success("Cambios guardados.");
            pegar();
            formBorra.resetFields();
            formModif.resetFields();
            setModif(false);
            setModalB(false);
            setModalM(false);
            setMostrarDatos(false);
            setEntidadM(null);
            setBancoNuevo(null);
        }        
        catch (err) {
            message.error(err.message);
            console.log(err);
        }
    }

    const handleBorra = async (v) => {
        try {
            await serv.borrar("s",entidadM.id)
            message.success(
                <p>
                    ¡Baja exitosa! <br />
                    Codigo: <b>{entidadM.codigo}</b> <br />
                    Nombre: <b>{entidadM.nombre}</b> <br />
                    Domicilio: <b>{entidadM.domicilio}</b> <br />
                    Empleados: <b>{entidadM.cantidadEmpleados}</b> <br />
                    Entidad asociada: <b>{entidadM.entidad || "ninguna."}</b> <br />
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



    function comprobarCambios () {
        if (
            (!formModif.getFieldValue("codigo") || (entidadM.codigo === formModif.getFieldValue("codigo")))
            &&
            (!formModif.getFieldValue("nombre") || (entidadM.nombre === formModif.getFieldValue("nombre")))
            &&
            (!formModif.getFieldValue("domicilio") || (entidadM.domicilio === formModif.getFieldValue("domicilio")))
            &&
            (!formModif.getFieldValue("cantidadEmpleados") || (entidadM.cantidadEmpleados === formModif.getFieldValue("cantidadEmpleados")))
            &&
            (!formModif.getFieldValue("entidad") || (entidadM.entidad === JSON.parse(formModif.getFieldValue("entidad")).id) || (!entidadM.entidad && bancoNuevo.id === -1))
        ) {
            message.warning("No se detectan cambios.");
        }
        else {
            setModalM(true);
        }
    }

    function comprobarCodigo () {
        pegar();
        if (sucursales.find(a => a.codigo === formNuevo.getFieldValue("codigo"))) {
            message.warning(`El código ingresado (${formNuevo.getFieldValue("codigo")}) está ocupado`);
        }
        else {
            setModalN(true);
        }
    }

    const pegar = async () => {
        setCargando(true);
        try {
            const resS = await serv.getAll("s");
            setSucursales(resS);
            console.log(sucursales);
        }
        catch (err) {
            setSucursales([]);            
            console.log(err);            
        }
        try {
            const resE = await serv.getAll("e");
            setEntidades(resE);
            console.log(entidades);
        }
        catch (err) {
            setEntidades([]);
            console.log(err);            
        }        
        setCargando(false);
    }

    useEffect(() => {pegar();}, [modo]);

    return (
        <>
            {!modo &&
                <> 
                    {cargando? 
                        <Spin size = "large" /> 
                        : 
                        <Todos datos = {sucursales} entidad = "sucursales" />                    
                    }
                </>
            }
            {modo === "nuevo" &&
                <>
                    <h1>Nueva sucursal </h1>
                    <Form {...layoutNuevo}
                        form = {formNuevo}
                        name = "formNuevo"
                        onFinish = {handleNuevo}
                        style = {{maxWidth: 600}}                    
                    >
                        <Form.Item name = "codigo" 
                            label = {<b>Codigo</b>}                            
                            rules = {[{
                                required: true,
                                message: "Codigo requerido"
                            }]}
                        >
                            <InputNumber style = {{width: 200}} 
                                placeholder = "Ingrese codigo"                                
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
                        <Form.Item name = "domicilio"
                            label = {<b>Domicilio</b>}                             
                            rules = {[{
                                required: true,
                                message: "Domicilio requerido"
                            }]}
                        >
                            <Input style = {{width: 200}}
                                placeholder = "Ingrese nombre"
                            />
                        </Form.Item>
                        <Form.Item name = "cantidadEmpleados"
                            label = {<b>Cantidad de empleados</b>}
                        >
                            <InputNumber style = {{width: 200}}
                                placeholder = "Ingrese empleados"
                            />                            
                        </Form.Item>
                        <Form.Item name = "entidad" 
                            label = {<b>Entidad</b>}>
                            <Select
                                allowClear
                                showSearch                                
                                placeholder = "Ingrese entidad"    
                            >
                                {entidades.map(x => (
                                    <Select.Option key = {x.id} value = {x.id}>
                                        Codigo: <b>{x.codigo}</b>, nombre: <b>{x.nombre}</b>
                                    </Select.Option>                                    
                                ))
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item wrapperCol = {{offset: 8, span: 16}}>
                            <Button type = "primary" onClick = {comprobarCodigo}>
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
                                <b>Codigo:</b> {formNuevo.getFieldValue("codigo")} <br />
                                <b>Nombre:</b> {formNuevo.getFieldValue("nombre")} <br />
                                <b>Domicilio:</b> {formNuevo.getFieldValue("domicilio")} <br />
                                <b>Cantidad de empleados:</b> {formNuevo.getFieldValue("cantidadEmpleados") || "0"} <br />
                                <b>Entidad asociada:</b> {formNuevo.getFieldValue("entidad") || "ninguna."} <br />
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
                    <h1>Buscar sucursal </h1>
                    <Form
                        form = {formBorra}
                        name = "formBorra"
                        onFinish = {handleBorra}                       
                    >
                        <Form.Item name = "a" label = {<b>Sucursal</b>}>
                            <Select
                                showSearch
                                style = {{width: 400, height: 25}}
                                onChange = {guardarID}
                                placeholder = "Ingrese sucursal"
                            >
                                {sucursales.map(x => (
                                        <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                            Codigo: <b>{x.codigo}</b>, nombre: <b>{x.nombre}</b>
                                        </Select.Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item name = "modalB">
                            <Modal
                                keyboard = {false}
                                width = {300}
                                closable = {false} maskClosable = {false}
                                open = {modalB}
                                title = "¿Confirma borrar sucursal?"
                                okText = "Aceptar"
                                cancelText = "Cancelar"
                                onCancel = {() =>setModalB(false)}
                                onOk = {() => formBorra.submit()}
                            >
                                {entidadM &&                                 
                                    <>  
                                        Codigo: <b>{entidadM.codigo}</b> <br />
                                        Nombre: <b>{entidadM.nombre}</b> <br />
                                        Domicilio: <b>{entidadM.domicilio}</b> <br />
                                        Cantidad de empleados: <b>{entidadM.cantidadEmpleados}</b> <br />
                                        Entidad: <b>{entidadM.nombreBanco || "no tiene."}</b>
                                    </>
                                }
                            </Modal>
                            <Button danger
                                disabled = {!mostrarDatos}
                                style = {{width: 130}}
                                type = "primary"
                                onClick = {() => setModalB(true)}
                            >
                                Borrar sucursal
                            </Button>
                        </Form.Item>
                    </Form>
                    {mostrarDatos &&
                        <>
                            <h2>Datos de la sucursal</h2>
                            <b>ID: </b>{entidadM.id} <br/>
                            <b>Codigo: </b>{entidadM.codigo} <br/>
                            <b>Nombre: </b> {entidadM.nombre} <br/>
                            <b>Domicilio: </b>{entidadM.domicilio} <br/> 
                            <b>Cantidad de empleados: </b>{entidadM.cantidadEmpleados} <br/> 
                            <b>Entidad: </b>{entidadM.nombreBanco || "no tiene."} <br/> 
                            <b>Asaltos recibidos: </b>{entidadM.asaltos.length} <br/>
                            <b>Contratos realizados: </b>{entidadM.contratos.length} <br/>
                            <br/>
                            <Button type = "primary"
                                style = {{width: 130}}
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
                                    name = "codigo"
                                    label = {<b>Codigo</b>}
                                >
                                    <InputNumber 
                                        placeholder = "Ingrese nuevo codigo" 
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
                                    name = "domicilio"
                                    label = {<b>Domicilio</b>} 
                                >
                                    <Input
                                        placeholder = "Ingrese nuevo domicilio"
                                        style = {{width: 200}}
                                    />                                    
                                </Form.Item>
                                <Form.Item
                                    name = "cantidadEmpleados"    
                                    label = {<b>Cantidad de empleados</b>}
                                >
                                    <InputNumber
                                        placeholder = "Ingrese empleados"
                                        style = {{width: 200}}
                                    />
                                </Form.Item>
                                <Form.Item name = "entidad" 
                                    label = {<b>Entidad</b>}>
                                    <Select
                                        allowClear
                                        showSearch
                                        style = {{width: 200}}
                                        placeholder = "Ingrese entidad"
                                        onChange = {guardarBanco}    
                                    >
                                        <Select.Option key = {-1} value = {JSON.stringify({id: -1, nombre: "sin banco"})}>
                                            <b>DESASOCIAR ENTIDAD</b>
                                        </Select.Option>
                                        {entidades.map(x => (
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
                                            {formModif.getFieldValue("domicilio") && 
                                                <>
                                                    Domicilio actual: <b>{entidadM.domicilio}</b> <br/>
                                                    Domicilio nuevo: <b>{formModif.getFieldValue("domicilio")}</b> <br/>
                                                    <br/>
                                                </>
                                            }
                                            {formModif.getFieldValue("cantidadEmpleados") && 
                                                <>
                                                    Cantidad de empleados actual: <b>{entidadM.cantidadEmpleados}</b> <br/>
                                                    Cantidad de empleados nueva: <b>{formModif.getFieldValue("cantidadEmpleados")}</b> <br/>
                                                    <br/>
                                                </>
                                            }
                                            {formModif.getFieldValue("entidad") && 
                                                <>
                                                    Entidad actual: <b>{entidadM.nombreBanco || "no tiene"}</b> <br/>
                                                    Entidad nueva: <b>{bancoNuevo.nombre}</b><br/>
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

export default Sucursales