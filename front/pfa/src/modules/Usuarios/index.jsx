import { useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { Button, Input, InputNumber, Form, message, Modal, Radio, Select, Spin, Typography } from "antd";
import serv from "../../services/serv";
import Todos from  "../../components/Todos";
import { AuthContext } from "../../components/AuthContext";

const layoutNuevo = {labelCol: {span: 8}, wrapperCol: {span: 16}};

const {Text} = Typography;

const roles = ["Vigilante", "Investigador", "Administrador"];

function Usuarios() {        
    const [usuarios, setUsuarios] = useState([]);
    const [rolElegido, setRolElegido] = useState(null);
    const {autenticado, rol, esAdmin, esInves, esVigi} = useContext(AuthContext);
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

    const guardarID = async (v) => {
        const u = JSON.parse(v);
        setEntidadM(u);
        setMostrarDatos(true);        
    }

    const handleChange = (v) => {
        setRolElegido(v.target.value);
    }

    const handleBorra = async (v) => {
        try {            
            await serv.borrar("u", entidadM.id);
            message.success(
                <p>
                    ¡Baja exitosa! <br />
                    Usuario: {entidadM.usr} <br />
                    Rol: {entidadM.rol} <br />

                </p>
            );
            pegar();
            formBorra.resetFields();
            setModalB(false);
            setMostrarDatos(false);
            setEntidadM(null);
        }
        catch (err) {
            message.error(err);
        }
    }

    async function handleModif (v) {
        try {
            v.id = entidadM.id;
            await serv.actualizar("u", v.id, v);
            message.success("Cambios guardados");
            pegar();
            formBorra.resetFields();
            formModif.resetFields();
            setModif(false);
            setModalB(false);
            setModalM(false);
            setMostrarDatos(false);
            setEntidadM(null);            
        }
        catch (e) {
            message.error(e.message);
            console.log(e);
        }
    }

    async function handleNuevo (x) {
        try {
            await serv.crear("u", x);
            message.success(
                <p>
                    ¡Alta exitosa! <br />
                    Usuario: {x.usr} <br /> 
                    Rol: {x.rol}
                </p>
            );
            formNuevo.resetFields();
            setModalN(false)
            setRolElegido(null);
            pegar();
        }
        catch (err) {
            message.error(err);
        }        
    }

    function comprobarCambios () {
        if (
            (!formModif.getFieldValue("usr") || (entidadM.usr === formModif.getFieldValue("usr")))
            &&
            (!formModif.getFieldValue("pwd") || (entidadM.pwd === formModif.getFieldValue("pwd")))
            &&
            (!formModif.getFieldValue("codigo") || (entidadM.codigo === formModif.getFieldValue("codigo")))
            &&
            (!formModif.getFieldValue("edad") || (entidadM.codigo === formModif.getFieldValue("edad")))
        ) {
            message.warning("No se detectan cambios");
        }
        else {
            setModalM(true);
        }
    }

    const pegar = async () => {
        setCargando(true);
        try {
            const res = await serv.getAll("u");
            setUsuarios(res);
            setCargando(false);
            //console.log(res);
        }
        catch (err) {
            setUsuarios([]);
            console.log(err);
            setCargando(false);
        }
    }

    useEffect(() => {
        pegar();
    }, [modo]);

    return (
        <>
            {!modo && (esAdmin() || esInves()) &&  /* VER TODOS */
                <>
                    {cargando?
                        <Spin size = "large" />
                        :
                        <Todos datos = {usuarios} entidad = "usuarios" />
                    }
                </>
            }
            {modo === "misservicios" && esVigi() && /* pantalla de vigilantes*/
                <>
                    <h1>Vista de vigilante</h1>
                </>
            }
            {modo === "nuevo" && (esAdmin()) && /* NUEVO */
                <>
                    <h1>Nuevo usuario </h1>
                    <Form {...layoutNuevo}
                        form={formNuevo}
                        name="formNuevoUsuario"
                        onFinish={handleNuevo}
                        style={{ maxWidth: 600 }}
                    >
                        <Form.Item name = "usr" label = {<b>Usuario</b>}
                            rules={[{
                                required: true,
                                message: "Usuario requerido."
                            }]}
                        >
                            <Input
                                placeholder="Ingrese usuario"
                                style={{ width: 200 }}

                            />
                        </Form.Item>
                        <Form.Item name = "pwd" label = {<b>Clave</b>}
                            rules={[{
                                required: true,
                                message: "Clave requerida."
                            }]}
                        >
                            <Input.Password
                                placeholder="Ingrese clave"
                                style={{ width: 200 }}
                            />
                        </Form.Item>
                        <Form.Item name = "rol" label = {<b>Rol</b>}>
                            <Radio.Group onChange={handleChange} value = {rolElegido} >
                                <Radio value = {roles[0]}> {roles[0]} </Radio>
                                <Radio value = {roles[1]}> {roles[1]} </Radio>
                                <Radio value = {roles[2]}> {roles[2]} </Radio>
                            </Radio.Group>                            
                        </Form.Item>                        
                        {rolElegido === "Vigilante" && (
                            <>
                                <Form.Item name = "codigo" label = {<b>Código</b>}>
                                    <InputNumber                                        
                                        placeholder="Ingrese código"
                                        style={{ width: 120 }}
                                    />
                                </Form.Item>
                                <Form.Item name = "edad" label = {<b>Edad</b>}
                                >
                                    <InputNumber                                        
                                        placeholder="Ingrese edad"
                                        style={{ width: 120 }}
                                    />
                                </Form.Item>
                            </>)
                        }
                        <Form.Item wrapperCol={{ offset: 8, span: 16, }}>                            
                            <Button type="primary" onClick = {() => setModalN(true)}>
                                Enviar
                            </Button>
                            <Modal
                                keyboard = {false}
                                width = {600}
                                closable = {false} maskClosable = {false}
                                open = {modalN}
                                title = "¿Confirmar alta?"
                                onCancel = {() => setModalN(false)}
                                okText = "Aceptar"
                                cancelText = "Cancelar"
                                onOk = {() => formNuevo.submit()}
                            >
                                <p>
                                    <b>Usuario: </b> {formNuevo.getFieldValue("usr")} <br />
                                    <b>Rol: </b> {formNuevo.getFieldValue("rol")} <br />
                                    {formNuevo.getFieldValue("codigo") && 
                                    formNuevo.getFieldValue("rol") === "Vigilante" &&
                                        <>
                                            <b>Codigo: </b> {formNuevo.getFieldValue("codigo")} <br /> 
                                        </>
                                    }
                                    {formNuevo.getFieldValue("edad") && 
                                    formNuevo.getFieldValue("rol") === "Vigilante" &&
                                        <>
                                            <b>Edad: </b> {formNuevo.getFieldValue("edad")} <br />
                                        </>
                                    }
                                    <br/>                                    
                                    <Text type = "danger">
                                        <b>ATENCION:</b> el rol del usuario no es editable. Asegúrese de haber elegido el rol correcto.
                                    </Text>
                                </p>
                            </Modal>
                            <Button onClick={() => formNuevo.resetFields()}>
                                Borrar
                            </Button>
                        </Form.Item>
                    </Form>
                </>
            }
            {modo === "buscar" && (esAdmin() || esInves()) &&
                <>
                    <h1>Buscar usuario</h1>
                    <Form
                        form = {formBorra}
                        name = "formBorra"
                        onFinish = {handleBorra}
                    >
                        <Form.Item
                            name = "a"
                            label = {<b>Usuario</b>}
                        >
                            <Select
                                showSearch
                                style = {{width: 400, height: 25}}
                                onChange = {guardarID}
                                placeholder = "Seleccione usuario"
                                >
                                    {
                                        usuarios.map(x => (
                                            <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                                Usuario: <b>{x.usr}</b>
                                                , rol: <b>{x.rol}</b>
                                                {x.codigo && <>, codigo: <b>{x.codigo}</b></>}                                                
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
                                title = "¿Confirma borrar usuario?"
                                okText = "Aceptar"
                                cancelText = "Cancelar"
                                onCancel = {() =>setModalB(false)}
                                onOk = {() => formBorra.submit()}
                            >
                                {entidadM &&                                 
                                    <>       
                                        <b>Usuario:</b> {entidadM.usr} <br />
                                        <b>Rol:</b> {entidadM.rol} <br />
                                        {entidadM.codigo && 
                                            <>
                                                Codigo: <b>{entidadM.codigo}</b> <br />
                                            </>
                                        }
                                        {entidadM.edad && 
                                            <>
                                                Edad: <b>{entidadM.edad}</b> <br />
                                            </>
                                        }
                                    </>
                                }
                            </Modal>
                            <Button danger
                                disabled = {!mostrarDatos}
                                style = {{width: 110}}
                                type = "primary"
                                onClick = {() => setModalB(true)}
                            >
                                Borrar usuario
                            </Button>
                        </Form.Item>
                        }
                    </Form>
                    {mostrarDatos && 
                        <>
                            <h2>Datos del usuario</h2>
                            ID: <b>{entidadM.id}</b> <br/>
                            Usuario: <b>{entidadM.usr}</b> <br/>
                            Rol: <b>{entidadM.rol}</b> <br/>
                            {entidadM.codigo && 
                                <>
                                    Codigo: <b>{entidadM.codigo}</b> <br />
                                </>
                            }
                            {entidadM.edad && 
                                <>
                                    Edad: <b>{entidadM.edad}</b> <br />
                                </>
                            }
                            {entidadM.contratos && 
                                <>
                                    Contratos asociados: <b>{entidadM.contratos.length}</b> <br />
                                </>
                            }
                            <br />
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
                                    name = "usr"
                                    label = {<b>Usuario</b>}
                                >
                                    <Input 
                                        placeholder = {entidadM.usr} 
                                        style = {{width: 200}}
                                    />
                                </Form.Item>
                                <Form.Item 
                                    name = "pwd"
                                    label = {<b>Contraseña</b>} 
                                >
                                    <Input.Password
                                        placeholder = {"Ingrese nueva contraseña"}
                                        style = {{width: 200}}
                                    />                                    
                                </Form.Item>
                                {entidadM.rol === "Vigilante" &&
                                    <>
                                        <Form.Item
                                            name = "codigo"    
                                            label = {<b>Código</b>}
                                        >
                                            <InputNumber
                                                placeholder = {entidadM.codigo}
                                                style = {{width: 200}}
                                            />
                                        </Form.Item>
                                        <Form.Item
                                            name = "edad"    
                                            label = {<b>Edad</b>}
                                        >
                                            <InputNumber
                                                placeholder = {entidadM.edad}
                                                style = {{width: 200}}
                                            />
                                        </Form.Item>
                                    </>
                                }
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

export default Usuarios