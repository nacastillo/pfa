import { useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { Button, Input, InputNumber, Form, message, Modal, Select, Spin, Table } from "antd";
import serv from "../../services/serv"
import Todos from  "../../components/Todos"
import { AuthContext } from "../../components/AuthContext";

const layoutNuevo = {labelCol: {span:8}, wrapperCol: {span:16}};

function Entidades() { 
    const [entidades, setEntidades] = useState([]);
    //
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
    const {esAdmin, esInves} = useContext(AuthContext);

    const guardarID = async (x) => {
        const y = JSON.parse(x)        
        setEntidadM(y);
        setMostrarDatos(true);
    }

    const handleNuevo = async (v) => {
        try {
            await serv.crear("e", v);
            message.success(
                <p>
                    ¡Alta exitosa! <br />
                    <b>Codigo:</b> {v.codigo} <br />
                    <b>Nombre:</b> {v.nombre} <br />
                    <b>Domicilio central:</b> {v.domicilio}
                </p>
            );
            formNuevo.resetFields();
            setModalN(false);
            pegar();
        }
        catch (err) {
            message.error(err.message)
        }
    }

    const handleModif = async (v) => {
        try {
            v.id = entidadM.id;
            await serv.actualizar("e",v.id, v);
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
            await serv.borrar("e", entidadM.id);
            message.success(
                <p>
                    ¡Baja exitosa! <br />
                    <b>Codigo:</b> {entidadM.codigo} <br />
                    <b>Domicilio central:</b> {entidadM.domicilio} <br />
                    <b>Nombre:</b> {entidadM.nombre}
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
            message.error(err.message)
        }
    }

    const comprobarCambios = () => {
        if (!formModif.getFieldValue("codigo") &&
            !formModif.getFieldValue("domicilio") &&
            !formModif.getFieldValue("nombre")        
        ) {
            message.error("No se detectan cambios.");
        }
        else {
            setModalM(true);
        }
    }   

    const pegar = async () => {
        setCargando(true);
        try {            
            const res = await serv.getAll("e");
            setEntidades(res);            
            console.log(res);
            setCargando(false);
        }
        catch (err) {
            setEntidades([]);
            // message.error(err.message);
            console.log(err);
            setCargando(false);
        }
    }

    useEffect(() => {
        pegar();
    }, []);

    return (
        <>
            {!modo && (esInves() || esAdmin()) &&
                <>
                    {cargando?
                    <Spin size = "large" />
                    :
                    <>
                        <Todos datos = {entidades} entidad = "entidades" />                        
                    </>}
                </>
            }
            {modo === "nuevo" && esAdmin() &&
                <>
                    <h1>Nueva entidad </h1>
                    <Form {...layoutNuevo}
                        form = {formNuevo}
                        name = "formNuevaEntidad"
                        onFinish = {handleNuevo}
                        style = {{maxWidth: 600}}                    
                    >
                        <Form.Item name = "codigo"
                            label = {<b>Codigo</b>}
                            rules ={[{
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
                            label = {<b>Domicilio central</b>}
                            rules = {[{
                                required: true,
                                message: "Domicilio requerido"
                            }]}                        
                        >
                            <Input style = {{width: 200}}
                                placeholder = "Ingrese domicilio central"
                            />                            
                        </Form.Item>
                        <Form.Item wrapperCol = {{offset: 8, span: 16}}>
                            <Button type = "primary" onClick = {() => setModalN(true)}>
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
                                <b>Domicilio central:</b> {formNuevo.getFieldValue("domicilio")} <br />
                                <b>Nombre:</b> {formNuevo.getFieldValue("nombre")} <br />
                            </Modal>
                            <Button onClick = {() => formNuevo.resetFields()}>
                                Borrar
                            </Button>                            
                        </Form.Item>
                    </Form>
                </>
            }
            {modo === "buscar" && (esAdmin() || esInves()) &&
                <>
                    <h1>Buscar entidad </h1>
                    <Form
                        form = {formBorra}
                        name = "formBorra"
                        onFinish = {handleBorra}
                    >
                        <Form.Item name = "a" label = {<b>Entidad</b>}>
                            <Select
                                showSearch
                                style = {{width: 400, height: 25}}
                                onChange = {guardarID}
                                placeholder = "Ingrese entidad"
                            >
                                {
                                    entidades.map (x => (
                                        <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                            Codigo: <b>{x.codigo}</b> - Nombre: <b>{x.nombre}</b>
                                        </Select.Option>
                                    ))
                                }
                            </Select>
                        </Form.Item>
                        {esAdmin() &&
                        <Form.Item name = "modalB">
                            <Modal
                                keyboard = {false}
                                width = {300}
                                closable = {false} maskClosable = {false}
                                open = {modalB}
                                title = "¿Confirma borrar entidad?"
                                okText = "Aceptar"
                                cancelText = "Cancelar"
                                onCancel = {() =>setModalB(false)}
                                onOk = {() => formBorra.submit()}
                            >
                                {entidadM &&                                 
                                    <>       
                                        <b>Codigo:</b> {entidadM.codigo} <br />
                                        <b>Nombre:</b> {entidadM.nombre} <br />
                                        <b>Domicilio central:</b> {entidadM.domicilio} <br />
                                    </>
                                }
                            </Modal>
                            <Button danger
                                disabled = {!mostrarDatos}
                                style = {{width: 130}}
                                type = "primary"
                                onClick = {() => setModalB(true)}
                            >
                                Borrar entidad
                            </Button>
                        </Form.Item>
                        }
                    </Form>
                    {mostrarDatos && 
                        <>
                            <h2>Datos de la entidad</h2>
                            <b>ID: </b>{entidadM.id} <br/>
                            <b>Codigo: </b>{entidadM.codigo} <br/>
                            <b>Nombre: </b>{entidadM.nombre} <br/> 
                            <b>Domicilio central: </b> {entidadM.domicilio} <br/>                            
                            <b>Sucursales asignadas: </b>{entidadM.sucursales.length} <br/>
                            <br/>
                            {esAdmin() &&
                            <Button type = "primary"
                                style = {{width: 110}}
                                htmlType = "button"
                                onClick= {() => setModif(!modif)}
                            >
                                Modificar
                            </Button>
                            }
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
                                <Form.Item
                                    name = "domicilio"    
                                    label = {<b>Domicilio central</b>}
                                >
                                    <Input
                                        placeholder = "Ingrese nuevo domicilio"
                                        style = {{width: 200}}
                                    />
                                </Form.Item>
                                <Form.Item wrapperCol = {{offset: 8, span: 16}}>
                                    <Button type = "primary" 
                                        onClick = {() => {
                                            //setModalM(true);
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
                                                    Domicilio central actual: <b>{entidadM.domicilio}</b> <br/>
                                                    Domicilio central nuevo: <b>{formModif.getFieldValue("domicilio")}</b> <br/>
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

export default Entidades