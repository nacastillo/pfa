import { useParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { Button, DatePicker, InputNumber, Form, message, Modal, Select, Spin } from "antd";
import serv from "../../services/serv"
import Todos from  "../../components/Todos";
import { AuthContext } from "../../components/AuthContext";

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
            <h2>Datos del asalto</h2>            
            Codigo: <b>{obj.codigo}</b> <br/>
            Fecha del asalto: <b>{obj.fecha}</b> <br/>
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
            {obj.juez ?
                <>
                    Clave del juez: <b>{obj.claveJuez}</b> <br/>
                    Nombre del juez: <b>{obj.nombreJuez}</b> <br/>
                </>
                :
                <>
                    Juez: <b>{obj.juez || "no tiene"}</b> <br/>
                </> 
            }            
            {obj.detenido ?
                <>
                    Codigo del detenido: <b>{obj.codigoDetenido}</b> <br/>
                    Nombre del detenido: <b>{obj.nombreDetenido}</b> <br/>
                </>
                :
                <>
                    Detenido: <b>{obj.detenido || "no tiene"}</b> <br/>
                </>
            }
            Fecha de la condena: <b>{obj.fechaCondena || "no tiene"}</b> <br/>
            <br/>
        </>
    )
}

function Asaltos() {    
    const [asaltos, setAsaltos] = useState([]);
    const [sucursales, setSucursales] = useState([]);
    const [jueces, setJueces] = useState([]);
    const [detenidos, setDetenidos] = useState([]);    
    const [sucursalNueva, setSucursalNueva] = useState(null);
    const [juezNuevo, setJuezNuevo] = useState(null);
    const [detenidoNuevo, setDetenidoNuevo] = useState(null);
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
    const {esAdmin, esInves, esVigi, getServicios } = useContext(AuthContext);

    async function completar (x) {
        let y = JSON.parse(x);
        try {
            const r = await serv.leer("a", y.id);
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

    function guardarJuez (x) {
        const y = JSON.parse(x)
        setJuezNuevo(y);
    }

    function guardarDetenido (x) {
        const y = JSON.parse(x)
        setDetenidoNuevo(y);
    }    

    function comprobarCambios () {
        if (
            (!formModif.getFieldValue("codigo") || (entidadM.codigo === formModif.getFieldValue("codigo")))
            &&
            (!formModif.getFieldValue("fecha") || (entidadM.fecha === armarFecha(formModif.getFieldValue("fecha"))))
            &&
            (!formModif.getFieldValue("sucursal") || (entidadM.sucursal === JSON.parse(formModif.getFieldValue("sucursal")).id) || (!entidadM.sucursal && sucursalNueva.id === -1))
            &&
            (!formModif.getFieldValue("juez") || (entidadM.juez === JSON.parse(formModif.getFieldValue("juez")).id) || (!entidadM.juez && juezNuevo.id === -1))
            &&
            (!formModif.getFieldValue("detenido") || (entidadM.detenido === JSON.parse(formModif.getFieldValue("detenido")).id) || (!entidadM.detenido && detenidoNuevo.id === -1))
            &&
            (!formModif.getFieldValue("fechaCondena") || (entidadM.fechaCondena === armarFecha(formModif.getFieldValue("fechaCondena"))))        
        ) {
            message.warning("No se detectan cambios");
        }
        else {
            setModalM(true);
        }
    }

    async function comprobarCodigo () {
        try {
            let x = await serv.getAll("a");
            if (x.find(a => a.codigo === formNuevo.getFieldValue("codigo"))) {
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
        let x = armarFecha(v.fecha);
        v.fecha = x;
        if (v.fechaCondena) {
            x = armarFecha(v.fechaCondena);
            v.fechaCondena = x;
        }
        try {
            await serv.crear("a", v);
            message.success(
                <p>
                    ¡Alta exitosa! <br />
                    Codigo: <b>{v.codigo}</b> <br/>
                    Fecha de asalto: <b>{v.fecha}</b> <br/>
                    ID de sucursal: <b>{v.sucursal || "no asignada"}</b> <br/> 
                    ID de juez: <b>{v.juez || "no asignado"}</b><br/>
                    ID de detenido: <b>{v.detenido || "no asignado"}</b> <br/>
                    Fecha de condena: <b>{v.fechaCondena || "no asignada"}</b> <br/>
                </p>
            );
            formNuevo.resetFields();
            setModalN(false);          
            pegar(); 
        }
        catch (err) {
            message.error(err.message);
        }
    }

    async function handleModif (v) {
        if (v.fecha) {
            let x = armarFecha(v.fecha);
            v.fecha = x;
        }
        if (v.fechaCondena) {
            let x = armarFecha(v.fechaCondena);
            v.fechaCondena = x;
        }
        if (v.sucursal !== undefined) {
            const a = JSON.parse(v.sucursal);
            v.sucursal = a.id;
        }
        if (v.juez !== undefined) {
            const a = JSON.parse(v.juez);
            v.juez = a.id;
        }
        if (v.detenido !== undefined) {
            const a = JSON.parse(v.detenido);
            v.detenido = a.id;
        }
        try {
            v.id = entidadM.id;
            await serv.actualizar("a",v.id, v);
            message.success("Cambios guardados.");
            pegar();
            formBorra.resetFields();
            formModif.resetFields();
            setModif(false);
            setModalB(false);
            setModalM(false);
            setMostrarDatos(false);
            setEntidadM(null);
            setSucursalNueva(null);
            setJuezNuevo(null);
            setDetenidoNuevo(null);
        }
        catch (e) {
            message.error(e.message);
            console.log(e);
        }
    }    


    async function handleBorra (v) {
        try {
            await serv.borrar("a", entidadM.id);
            message.success("¡Baja exitosa!");
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
    async function pegar () {
        setCargando(true);
        try {
            const resA = await serv.getAll("a");
            setAsaltos(resA);            
            //console.log(asaltos);
        }
        catch (err) {
            setAsaltos([])
            //console.log(err);
        }
        try {
            const resS = await serv.getAll("s");
            setSucursales(resS);
            //console.log(sucursales);
        }
        catch (err) {
            setSucursales([]);
            //console.log(err);
        } 
        try {
            const resJ = await serv.getAll("j");
            setJueces(resJ);
            //console.log(jueces);
        }
        catch (err) {
            setJueces([]);
            //console.log(err);
        }
        try {
            const resD = await serv.getAll("d");
            setDetenidos(resD);
            //console.log(detenidos);
        } 
        catch (err) {
            setDetenidos([]);
            //console.log(err);
        }
        setCargando(false);
    }    
    
    useEffect(() => {pegar();}, [modo])        

    return (
        <>
            {!modo && (esAdmin() || esInves()) &&
                <>
                    {cargando? 
                        <Spin size = "large" /> 
                        : 
                        <Todos datos = {asaltos} entidad = "asaltos"/>
                    }
                </>
            }
            {modo === "nuevo" && esAdmin() &&
                <>
                    <h1>Dar de alta asalto</h1>
                    <Form {...layoutNuevo} 
                        form = {formNuevo}
                        name = "formNuevoAsalto"
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
                            label = {<b>Fecha del asalto</b>}
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
                        <Form.Item name = "juez" 
                            label = {<b>Juez</b>}
                        >
                            <Select
                                allowClear
                                showSearch
                                style = {{width: 300}}
                                placeholder = "Seleccione juez"    
                            >
                                {jueces.map(x => (
                                    <Select.Option key = {x.id} value = {x.id}>
                                        Clave: <b>{x.clave}</b>, nombre: <b>{x.nombre}</b>
                                    </Select.Option>                                    
                                ))
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item name = "detenido" 
                            label = {<b>Detenido</b>}>
                            <Select
                                allowClear
                                showSearch
                                style = {{width: 300}}
                                placeholder = "Seleccione detenido"    
                            >
                                {detenidos.map(x => (
                                    <Select.Option key = {x.id} value = {x.id}>
                                        Codigo: <b>{x.codigo}</b>, nombre: <b>{x.nombre}</b>
                                    </Select.Option>                                    
                                ))
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item name = "fechaCondena"
                            label = {<b>Fecha de la condena</b>}                        
                        >
                            <DatePicker 
                                placeholder = "Seleccione fecha"
                                allowClear                            
                            ></DatePicker>
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
                                Fecha de asalto: <b>{formNuevo.getFieldValue("fecha") && armarFecha(formNuevo.getFieldValue("fecha"))}</b> <br />
                                Sucursal: <b>{formNuevo.getFieldValue("sucursal") || "no asignada."}</b> <br />
                                Juez: <b>{formNuevo.getFieldValue("juez") || "no asignado."}</b> <br />
                                Detenido: <b>{formNuevo.getFieldValue("detenido") || "no asignado."}</b> <br />
                                Fecha de condena: <b>{formNuevo.getFieldValue("fechaCondena") ? armarFecha(formNuevo.getFieldValue("fechaCondena")) : "no tiene"}</b> <br />
                            </Modal>
                            <Button onClick = {() => form.resetFields()}>
                                Borrar
                            </Button>
                        </Form.Item>
                    </Form>                
                </>
            }
            {modo === "buscar" && (esAdmin() || esInves()) &&
                <>
                    <h1>Buscar asalto</h1>
                    <Form
                        form = {formBorra}
                        name = "formBorra"
                        onFinish = {handleBorra}                       
                    >
                        <Form.Item name = "a" label = {<b>Asalto</b>}>
                            <Select
                                showSearch
                                style = {{width: 400, height: 25}}
                                onChange = {completar}
                                placeholder = "Seleccione asalto"
                            >
                                {asaltos.map(x => (
                                        <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                            Codigo: <b>{x.codigo}</b>, 
                                            fecha: <b>{x.fecha}</b>
                                            {x.detenido && <>, detenido: <b>{x.detenido}</b> </>}
                                            {x.sucursal && <>, sucursal: <b>{x.sucursal}</b> </>}
                                        </Select.Option>
                                ))}
                            </Select>
                        </Form.Item>
                        {esAdmin() &&
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
                        }
                    </Form>
                    {mostrarDatos && 
                        <>
                            {mostrar(entidadM)}                        
                            {esAdmin() &&                         
                            <Button type = "primary"
                                style = {{width: 130}}
                                htmlType = "button"
                                onClick = {() => setModif(!modif)}
                            >
                                Modificar                            
                            </Button>                        
                        }
                        </>
                    }
                    {modif && esAdmin() &&
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
                                <Form.Item name = "juez" 
                                    label = {<b>Juez</b>}
                                >
                                    <Select
                                        allowClear
                                        showSearch
                                        style = {{width: 300}}
                                        placeholder = "Seleccione juez"    
                                        onChange = {guardarJuez}
                                    >
                                        <Select.Option key = {-1} value = {JSON.stringify({id: -1, nombre: "sin juez"})}>
                                            <b>DESASOCIAR JUEZ</b>
                                        </Select.Option>
                                        {jueces.map(x => (
                                            <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                                Clave: <b>{x.clave}</b>, nombre: <b>{x.nombre}</b>
                                            </Select.Option>                                    
                                        ))
                                        }
                                    </Select>
                                </Form.Item>
                                <Form.Item name = "detenido" 
                                    label = {<b>Detenido</b>}>
                                    <Select
                                        allowClear
                                        showSearch
                                        style = {{width: 300}}
                                        placeholder = "Seleccione detenido"  
                                        onChange = {guardarDetenido}
                                    >
                                        <Select.Option key = {-1} value = {JSON.stringify({id: -1, nombre: "sin detenido"})}>
                                            <b>DESASOCIAR DETENIDO</b>
                                        </Select.Option>
                                        {detenidos.map(x => (
                                            <Select.Option key = {x.id} value = {JSON.stringify(x)}>
                                                Codigo: <b>{x.codigo}</b>, nombre: <b>{x.nombre}</b>
                                            </Select.Option>                                    
                                        ))
                                        }
                                    </Select>
                                </Form.Item>
                                <Form.Item name = "fechaCondena"
                                    label = {<b>Fecha de la condena</b>}                        
                                >
                                    <DatePicker 
                                        placeholder = "Seleccione fecha"
                                        allowClear                            
                                    ></DatePicker>
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
                                            {formModif.getFieldValue("sucursal") && 
                                                <>
                                                    Sucursal actual: <b>{entidadM.nombreSucursal || "no tiene"}</b> <br/>
                                                    Sucursal nueva: <b>{sucursalNueva.nombre}</b> <br/>
                                                    <br/>
                                                </>
                                            }
                                            {formModif.getFieldValue("juez") && 
                                                <>
                                                    Juez actual: <b>{entidadM.nombreJuez || "no tiene"}</b> <br/>
                                                    Juez nuevo: <b>{juezNuevo.nombre}</b> <br/>
                                                    <br/>
                                                </>
                                            }
                                            {formModif.getFieldValue("detenido") && 
                                                <>
                                                    Detenido actual: <b>{entidadM.nombreDetenido || "no tiene"}</b> <br/>
                                                    Detenido nuevo: <b>{detenidoNuevo.nombre}</b> <br/>
                                                    <br/>
                                                </>
                                            }
                                            {formModif.getFieldValue("fechaCondena") && 
                                                <>
                                                    Fecha de condena actual: <b>{entidadM.fechaCondena}</b> <br/>
                                                    Fecha de condena nuevo: <b>{armarFecha(formModif.getFieldValue("fechaCondena"))}</b><br/>
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

export default Asaltos