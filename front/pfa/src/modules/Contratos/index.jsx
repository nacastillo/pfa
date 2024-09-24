import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { Button, Checkbox, DatePicker, InputNumber, Form, message, Modal, Select, Spin } from "antd";
import serv from "../../services/serv";
import Todos from "../../components/Todos";


const layoutNuevo = {labelCol: {span: 8}, wrapperCol: {span: 16}};

function armarFecha (input) {
    let dia = input.$D < 9 ? `0${input.$D}` : `${input.$D}`
    let mes = input.$M < 9 ? `0${input.$M+1}` : `${input.$M+1}`
    let anio = `${input.$y}`;
    return (anio + "-" + mes + "-" + dia);
}

function mostrar (obj) {
    return (
        <>
            <h2>Datos del contrato</h2>
            ID: <b>{obj.id}</b> <br />
            Codigo: <b>{obj.codigo}</b> <br/>
            Fecha: <b>{obj.fecha}</b> <br/>
            {obj.sucursal ? 
                <>
                    Codigo de la sucursal: <b>{obj.codigoSucursal}</b> <br/>
                    Nombre de la sucursal: <b>{obj.nombreSucursal}</b> <br/>
                    {obj.entidad && 
                        <>
                            Entidad asociada: <b>{obj.nombreEntidad}</b> <br />
                        </>
                    }
                </>
                :
                <>
                    Sucursal: <b>no tiene</b> <br/>
                </>
            }
            {obj.vigilante ?
                <>
                    Usuario del vigilante: <b>{obj.usrVigilante}</b> <br/>
                    {obj.codigoVigilante && 
                        <>
                            Codigo del vigilante: <b>{obj.codigoVigilante}</b> <br/>
                        </>
                    }
                    {obj.edadVigilante && 
                        <>
                            Edad del vigilante: <b>{obj.edadVigilante}</b> <br/>
                        </>
                    }
                </>
                :
                <>
                    Vigilante: <b>no tiene</b> <br/>
                </>
            }       
            Armado: <b>{obj.armado? "sí" : "no"}</b> <br/>
            <br/>
        </>
    )
}

function Contratos() {
    const [contratos, setContratos] = useState([]);
    const [sucursales, setSucursales] = useState([]);
    const [vigilantes, setVigilantes] = useState([]);
    const [sucursalNueva, setSucursalNueva] = useState(null);
    const [vigilanteNuevo, setVigilanteNuevo] = useState(null);    
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

    async function completar (x) {
        let y = JSON.parse(x);
        try {
            const r = await serv.leer("c", y.id);
            y = r;
        }
        catch (err) {
            console.log(err);
        }
        setEntidadM(y);
        setMostrarDatos(true);
    }

    function guardarSucursal (x) {    
        const y = JSON.parse(x)
        setSucursalNueva(y);
    }    

    function guardarVigilante (x) {
        const y = JSON.parse(x)
        setVigilanteNuevo(y);
    }    

    function comprobarCambios () {
        if (
            (!formModif.getFieldValue("codigo") || (entidadM.codigo === formModif.getFieldValue("codigo")))
            &&
            (!formModif.getFieldValue("fecha") || (entidadM.fecha === armarFecha(formModif.getFieldValue("fecha"))))
            &&
            (!formModif.getFieldValue("vigilante") || (entidadM.vigilante === JSON.parse(formModif.getFieldValue("vigilante")).id) || (!entidadM.vigilante && vigilanteNuevo.id === -1))
            &&
            (!formModif.getFieldValue("sucursal") || (entidadM.sucursal === JSON.parse(formModif.getFieldValue("sucursal")).id) || (!entidadM.sucursal && sucursalNueva.id === -1))
            &&            
            (formModif.getFieldValue("armado") !== null && (entidadM.armado === formModif.getFieldValue("armado")))
        ) {
            message.warning("No se detectan cambios");
        }
        else {
            setModalM(true);
        }
    }    
    
    async function comprobarCodigo () {
        try {
            let c = await serv.getAll("c");
            if (c.find(a => a.codigo === formNuevo.getFieldValue("codigo"))) {
                message.warning(`El código ingresado (${formNuevo.getFieldValue("codigo")}) está ocupado`);
            }
            else {
                setModalN(true);
            }
        }
        catch (e) {
            console.log(e);   
            setModalN(true);
        }
    }

    async function handleNuevo (v) {
        if (!v.armado) {
            v.armado = false;
        }
        let x = armarFecha(v.fecha);
        v.fecha = x;
        try {
            await serv.crear("c", v);
            message.success(
                <p>
                    ¡Alta exitosa! <br />
                    Codigo: <b>{v.codigo}</b> <br/>
                    Fecha: <b>{v.fecha}</b> <br/>
                    ID de vigilante: <b>{v.vigilante || "no asignado"}</b> <br/>
                    ID de sucursal: <b>{v.sucursal || "no asignada"}</b> <br/>
                    Armado: <b>{v.armado? "sí" : "no"}</b> <br />
                </p>
            );
            formNuevo.resetFields();
            setModalN(false);
            pegar();
        }
        catch (e) {
            message.error(e.message);
            console.log(e);
        }

    }

    async function handleModif (v) {
        if (v.fecha) {
            let x = armarFecha(v.fecha);
            v.fecha = x;
        }
        if (v.vigilante !== undefined) {
            const a = JSON.parse(v.vigilante);
            v.vigilante = a.id;
        }        
        if (v.sucursal !== undefined) {
            const a = JSON.parse(v.sucursal);
            v.sucursal = a.id;
        }
        if (v.armado === null) {
            v.armado = entidadM.armado;
        }
        try {
            v.id = entidadM.id;
            await serv.actualizar("c",v.id, v);
            message.success("Cambios guardados.")
            pegar();
            formBorra.resetFields();
            formModif.resetFields();
            setModif(false);
            setModalB(false);
            setModalM(false);
            setMostrarDatos(false);
            setEntidadM(null);
            setVigilanteNuevo(null);
            setSucursalNueva(null);
        }
        catch (e) {
            message.error(e.message);
            console.log(e);            
        }        
    }

    async function handleBorra (v) {
        try {
            await serv.borrar("c", entidadM.id);
            message.success("¡Baja exitosa!");
            pegar();
            formBorra.resetFields();
            setModif(false);
            setModalB(false);
            setMostrarDatos(false);
            setEntidadM(null);
        }
        catch (e) {
            message.error(e.message);
        }
    }

    async function pegar () {
        setCargando(true);
        try {
            const resC = await serv.getAll("c");
            setContratos(resC);                        
        }
        catch (err) {
            setContratos([])            
        }
        try {
            const resS = await serv.getAll("s");
            setSucursales(resS);            
        }
        catch (err) {
            setSucursales([]);
            
        } 
        try {
            const resU = await serv.getAll("u");
            const v = resU.filter(u => u.rol === "Vigilante");  
            setVigilantes(v);            
        }
        catch (err) {
            setVigilantes([]);            
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
                    <Todos datos = {contratos} entidad = "contratos"/>
                }
            </>
            }

            {modo === "nuevo" &&
                <>
                    <h1>Dar de alta contrato</h1>
                    <Form {...layoutNuevo} 
                        form = {formNuevo}
                        name = "formNuevo"
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
                        <Form.Item name = "fecha"
                            label = {<b>Fecha</b>}
                            rules = {[{
                                required: true,
                                message: "Fecha requerida"
                            }]} 
                        >
                            <DatePicker 
                                placeholder = "Seleccione fecha"                        
                                allowClear  
                            />                    
                        </Form.Item>
                        <Form.Item name = "vigilante"
                            label = {<b>Vigilante</b>}>
                            <Select
                                allowClear
                                showSearch
                                style = {{width: 300}}
                                placeholder = "Seleccione vigilante"    
                            >
                                {vigilantes.map(x => (
                                    <Select.Option key = {x.id} value = {x.id}>
                                        Codigo: <b>{x.codigo}</b>, usuario: <b>{x.usr}</b>
                                    </Select.Option>                                    
                                ))
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item name = "sucursal" 
                            label = {<b>Sucursal</b>}>
                            <Select
                                allowClear
                                showSearch
                                style = {{width: 300}}
                                placeholder = "Seleccione sucursal"    
                            >
                                {sucursales.map(x => (
                                    <Select.Option key = {x.id} value = {x.id}>
                                        Codigo: <b>{x.codigo}</b>, nombre: <b>{x.nombre}</b>
                                    </Select.Option>                                    
                                ))
                                }
                            </Select>
                        </Form.Item>                        
                        <Form.Item name = "armado"
                            label = {<b>Armado</b>}                        
                        >
                            <Select style = {{width: 80}}>
                                <Select.Option key = {1} value = {true}>
                                    Si
                                </Select.Option>
                                <Select.Option key = {2} value = {false}>
                                    No
                                </Select.Option>
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
                                Codigo: <b>{formNuevo.getFieldValue("codigo")}</b> <br />                                
                                Fecha: <b>{formNuevo.getFieldValue("fecha") && armarFecha(formNuevo.getFieldValue("fecha"))}</b> <br />
                                Detenido: <b>{formNuevo.getFieldValue("vigilante") || "no asignado."}</b> <br />
                                Sucursal: <b>{formNuevo.getFieldValue("sucursal") || "no asignada."}</b> <br />
                                Armado: <b>{formNuevo.getFieldValue("armado") ? "sí" : "no"}</b>
                            </Modal>
                            <Button onClick = {() => form.resetFields()}>
                                Borrar
                            </Button>
                        </Form.Item>
                    </Form>
                    
                    
                </>
            }
            {modo === "buscar" &&
                <>
                    <h1>Buscar contrato </h1>
                    <Form
                        form = {formBorra}
                        name = "formBorra"
                        onFinish = {handleBorra}                       
                    >
                        <Form.Item name = "a" label = {<b>Contrato</b>}>
                            <Select
                                showSearch
                                style = {{width: 400, height: 25}}
                                onChange = {completar}
                                placeholder = "Seleccione contrato"
                            >
                                {contratos.map(x => (
                                        <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                            Codigo: <b>{x.codigo}</b>, 
                                            fecha: <b>{x.fecha}</b>
                                            {x.vigilante && <>, vigilante: <b>{x.vigilante}</b> </>}
                                            {x.sucursal && <>, sucursal: <b>{x.sucursal}</b> </>}
                                        </Select.Option>
                                ))}
                            </Select>
                        </Form.Item>
                        <Form.Item name = "modalB">
                            <Modal
                                keyboard = {false}
                                width = {400}
                                closable = {false} maskClosable = {false}
                                open = {modalB}
                                title = "¿Confirma borrar asalto?"
                                okText = "Aceptar"
                                cancelText = "Cancelar"
                                onCancel = {() =>setModalB(false)}
                                onOk = {() => formBorra.submit()}
                            >
                                {entidadM &&                                 
                                    <>  
                                        Codigo: <b>{entidadM.codigo}</b> <br />
                                        Fecha de asalto: <b>{entidadM.fecha}</b> <br />
                                        Sucursal: <b>{
                                            entidadM.sucursal ?
                                                <>
                                                    {entidadM.nombreSucursal} ({entidadM.codigoSucursal})
                                                    {entidadM.entidad && <> ({entidadM.nombreEntidad})</>}
                                                </>
                                                :
                                                <>no tiene</>
                                        } </b> <br/>
                                        Juez: <b>{
                                            entidadM.juez ?
                                                <>
                                                    {entidadM.nombreJuez} ({entidadM.claveJuez})
                                                </>
                                                :
                                                <>no tiene</>
                                        } </b> <br/>
                                        Detenido: <b>{
                                            entidadM.detenido ?
                                                <>
                                                    {entidadM.nombreDetenido} ({entidadM.codigoDetenido})
                                                </>
                                                :
                                                <>no tiene</>
                                        } </b> <br/>
                                        Fecha de condena: <b>{entidadM.fechaCondena || "no tiene"}</b> <br />
                                    </>
                                }
                            </Modal>
                            <Button danger
                                disabled = {!mostrarDatos}
                                style = {{width: 130}}
                                type = "primary"
                                onClick = {() => setModalB(true)}
                            >
                                Borrar asalto
                            </Button>
                        </Form.Item>
                    </Form>
                    {mostrarDatos && 
                        <>
                            {mostrar(entidadM)}
                            <Button type = "primary"
                                style = {{width: 130}}
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
                                        style = {{width: 200}}
                                        placeholder = "Ingrese codigo"
                                    />
                                </Form.Item>
                                <Form.Item 
                                    name = "fecha"
                                    label = {<b>Fecha del asalto</b>}                                     
                                >
                                    <DatePicker 
                                        placeholder = "Seleccione fecha"                        
                                        allowClear  
                                    />                    
                                </Form.Item>
                                <Form.Item name = "vigilante" 
                                    label = {<b>Detenido</b>}>
                                    <Select
                                        allowClear
                                        showSearch
                                        style = {{width: 300}}
                                        placeholder = "Seleccione vigilante"  
                                        onChange = {guardarVigilante}
                                    >
                                        <Select.Option key = {-1} value = {JSON.stringify({id: -1, usr: "sin vigilante"})}>
                                            <b>DESASOCIAR VIGILANTE</b>
                                        </Select.Option>
                                        {vigilantes.map(x => (
                                            <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                                Codigo: <b>{x.codigo}</b>, Usuario: <b>{x.usr}</b>
                                            </Select.Option>                                    
                                        ))
                                        }
                                    </Select>
                                </Form.Item>
                                <Form.Item 
                                    name = "sucursal" 
                                    label = {<b>Sucursal</b>}>
                                    <Select
                                        allowClear
                                        showSearch
                                        style = {{width: 300}}
                                        placeholder = "Seleccione sucursal"
                                        onChange = {guardarSucursal}
                                    >
                                        <Select.Option key = {-1} value = {JSON.stringify({id: -1, nombre: "sin sucursal"})}>
                                            <b>DESASOCIAR SUCURSAL</b>
                                        </Select.Option>
                                        {sucursales.map(x => (
                                            <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                                Codigo: <b>{x.codigo}</b>, nombre: <b>{x.nombre}</b>
                                            </Select.Option>                                    
                                        ))
                                        }
                                    </Select>
                                </Form.Item>                                
                                <Form.Item name = "armado"
                                    label = {<b>Armado</b>}                        
                                >
                                    <Select style = {{width: 80}}>
                                        <Select.Option key = {1} value = {true}>
                                            Si
                                        </Select.Option>
                                        <Select.Option key = {2} value = {false}>
                                            No
                                        </Select.Option>
                                    </Select>                          
                                </Form.Item>
                                <Form.Item wrapperCol={{offset: 8, span: 16}}>
                                    <Button type = "primary"
                                        onClick = {() => comprobarCambios()}    
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
                                            {formModif.getFieldValue("fecha") && 
                                                <>
                                                    Fecha de asalto actual: <b>{entidadM.fecha}</b> <br/>
                                                    Fecha de asalto nuevo: <b>{armarFecha(formModif.getFieldValue("fecha"))}</b><br/>
                                                    <br/>
                                                </>
                                            }
                                            {formModif.getFieldValue("vigilante") && 
                                                <>
                                                    Vigilante actual: <b>{entidadM.usrVigilante || "no tiene"}</b> <br/>
                                                    Vigilante nuevo: <b>{vigilanteNuevo.usr}</b> <br/>
                                                    <br/>
                                                </>
                                            }
                                            {formModif.getFieldValue("sucursal") && 
                                                <>
                                                    Sucursal actual: <b>{entidadM.nombreSucursal || "no tiene"}</b> <br/>
                                                    Sucursal nueva: <b>{sucursalNueva.nombre}</b> <br/>
                                                    <br/>
                                                </>
                                            }                                            
                                            {formModif.getFieldValue("armado") != null && 
                                                <>
                                                    Incluye arma actualmente: <b>{entidadM.armado? "sí" : "no"}</b> <br/>
                                                    Incluirá arma: <b>{formModif.getFieldValue("armado")? "sí" : "no"}</b><br/>
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

export default Contratos