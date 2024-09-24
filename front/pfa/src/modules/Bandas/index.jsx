import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Button, Input, InputNumber, Form, message, Modal, Select, Spin } from "antd";
import serv from "../../services/serv";
import Todos from  "../../components/Todos";

const layoutNuevo = {labelCol: {span: 8}, wrapperCol: {span: 16}};

function Bandas() {   
    const [bandas, setBandas] = useState([]);    
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
            await serv.crear("b", v);
            message.success(
                <p>
                    ¡Alta exitosa! <br/>
                    <b>Numero:</b> {v.numero} <br />
                    <b>Nombre:</b> {v.nombre} <br />
                 </p>
            );
            formNuevo.resetFields();
            setModalN(false);
            pegar();
        }
        catch (err) {
            message.error(err)
        }        
    }

    const handleModif = async (v) => {
        try {
            v.id = entidadM.id;
            await serv.actualizar("b",v.id, v);
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
            await serv.borrar("b", entidadM.id);
            message.success(
                <p>
                    ¡Baja exitosa! <br />
                    <b>Numero:</b> {entidadM.numero} <br/>
                    <b>Nombre:</b> {entidadM.nombre} <br/>                    
                </p>
            );         
            pegar();            
            formBorra.resetFields();     
            setModif(false);
            setModalB(false);
            setMostrarDatos(false)
            setEntidadM(null);
        }   
        catch (err) {
            message.error(err.message);
        }
    }

    const comprobarCambios = () => {
        if (!formModif.getFieldValue("numero") &&
            !formModif.getFieldValue("nombre")            
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
            const res = await serv.getAll("b");
            setBandas(res);
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
                        <Todos datos = {bandas} entidad = "bandas"/>
                    }
                </>                
            }
            {modo === "nuevo" &&
                <>
                    <h1>Nueva banda</h1>
                    <Form {...layoutNuevo}
                        form = {formNuevo}
                        name ="formNuevaBanda"
                        onFinish = {handleNuevo}
                        style = {{maxWidth: 600}}                    
                    >
                        <Form.Item name = "numero" 
                            label = {<b>Número</b>}
                            rules = {[{
                                required: true,
                                message: "Número requerido."
                            }]}
                        >
                            <InputNumber 
                                placeholder = "Ingrese número"
                                style = {{width: 200}}                                
                                />               
                        </Form.Item>
                        <Form.Item name = "nombre" 
                            label = {<b>Nombre</b>} 
                            rules = {[{
                                required: true,
                                message: "Nombre requerido."
                            }]}
                        >
                            <Input 
                                placeholder = "Ingrese nombre"
                                style = {{width: 200}}
                            />
                        </Form.Item>
                        <Form.Item wrapperCol = {{offset: 8, span: 16}}>
                            <Button type = "primary" onClick = {() => setModalN(true)}>
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
                                <b>Número:</b> {formNuevo.getFieldValue("numero")} <br />
                                <b>Nombre:</b> {formNuevo.getFieldValue("nombre")}
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
                    <h1>Buscar banda </h1>
                    <Form
                        form = {formBorra}
                        name = "formBorra"
                        onFinish = {handleBorra}
                    >
                        <Form.Item name = "a" label = {<b>Banda</b>}>
                            <Select                                
                                showSearch
                                style = {{width: 300, height: 25}}
                                onChange = {guardarID}
                                placeholder = "Ingrese banda"
                            >
                                {bandas.map (x => (
                                    <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                        Numero: <b>{x.numero}</b>, nombre: <b>{x.nombre}</b>
                                    </Select.Option>))}
                            </Select>
                        </Form.Item>
                        <Form.Item name = "modalB">                            
                            <Modal
                                keyboard = {false}
                                width = {300}
                                closable = {false} maskClosable = {false}
                                open ={modalB}
                                title = "¿Confirma borrar banda?"
                                okText = "Aceptar"
                                cancelText = "Cancelar"
                                onCancel = {() => setModalB(false)}
                                onOk = {() => formBorra.submit()}
                            >   
                                {entidadM &&
                                    <>
                                <b>Numero:</b> {entidadM.numero} <br />
                                <b>Nombre:</b> {entidadM.nombre} <br />
                                    </>                            
                                }
                            </Modal>
                            <Button danger
                                disabled = {!mostrarDatos} 
                                style = {{width: 110}} 
                                type = "primary" 
                                onClick = {() => setModalB(true)}
                            >
                                Borrar banda
                            </Button>
                        </Form.Item>
                    </Form>
                    {mostrarDatos && 
                        <>
                            <h2>Datos de banda:</h2>
                            <b>ID:</b> {entidadM.id} <br />
                            <b>Numero:</b> {entidadM.numero} <br />
                            <b>Nombre:</b> {entidadM.nombre} <br />   
                            <br/>
                            <Button style = {{width: 110}} 
                                type = "primary" 
                                htmlType="button" 
                                onClick={() => setModif(!modif)}>
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
                                    name = "numero"
                                    label = {<b>Numero</b>}
                                >
                                    <Input 
                                        placeholder = "Ingrese nuevo numero" 
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
                                <Form.Item wrapperCol = {{offset: 8, span: 16}}>
                                    <Button type = "primary" 
                                        onClick = {comprobarCambios}
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
                                            {formModif.getFieldValue("numero") && 
                                                <>
                                                    Numero actual: <b>{entidadM.numero}</b> <br />
                                                    Numero nuevo: <b>{formModif.getFieldValue("numero")}</b> <br/>
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

export default Bandas