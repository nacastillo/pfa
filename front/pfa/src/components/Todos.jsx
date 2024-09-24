import {Table} from "antd"

function setCol (t, di, k, w, r) {
    return {
        title: t,
        dataIndex: di,
        key: k,
        width: w,
        render: r
    }
}

function setHeader (ent) {
    let col = [];
    col.push(setCol("ID", "id", "id", 10));
    switch (ent) {
        case "usuarios":
            col.push(setCol("Usuario", "usr", "usr", 30));
            col.push(setCol("Rol", "rol", "rol", 30));
            col.push(setCol("Código", "codigo", "codigo", 10, r => r || "no tiene"));
            col.push(setCol("Edad", "edad", "edad", 10, r => r || "no tiene"));
            col.push(setCol("Contratos", "contratos", "contratos", 10, r => r? r.length : "no tiene"));
            break;
        case "sucursales":
            col.push(setCol("Código", "codigo", "codigo", 10));
            col.push(setCol("Nombre", "nombre", "nombre", 20));
            col.push(setCol("Domicilio", "domicilio", "domicilio", 30));
            col.push(setCol("Entidad", "entidad", "entidad", 20, r => r || "no tiene"));            
            col.push(setCol("Empleados", "cantidadEmpleados", "cantidadEmpleados", 10));
            col.push(setCol("Contratos", "contratos", "contratos", 20, r => r.length));
            col.push(setCol("Asaltos", "asaltos", "asaltos", 20, r => r.length));
            break;
        case "bandas":
            col.push(setCol("Numero", "numero", "numero", 10));
            col.push(setCol("Nombre", "nombre", "nombre", 20));            
            col.push(setCol("Miembros", "miembros", "miembros", 20, r => r.length));
            break;
        case "jueces":
            col.push(setCol("Clave", "clave", "clave", 10));
            col.push(setCol("Nombre", "nombre", "nombre", 20));
            col.push(setCol("Años de servicio", "aniosServicio", "aniosServicio", 20));
            col.push(setCol("Asaltos", "asaltos", "asaltos", 20, r => r.length));
            break;        
        case "contratos":
            col.push(setCol("Código", "codigo", "codigo", 10));
            col.push(setCol("Fecha", "fecha", "fecha", 25));
            col.push(setCol("Vigilante", "vigilante", "vigilante", 25, r => r || "no tiene"));
            col.push(setCol("Sucursal", "sucursal", "sucursal", 25, r => r || "no tiene"));
            col.push(setCol("Armado", "armado", "armado", 5, r => r? "sí" : "no"));
            break;
        case "entidades":
            col.push(setCol("Código", "codigo", "codigo", 10));
            col.push(setCol("Nombre", "nombre", "nombre", 20));
            col.push(setCol("Domicilio central", "domicilio", "domicilio", 30));
            col.push(setCol("Sucursales", "sucursales", "sucursales", 20, r => r.length));
            break;
        case "detenidos":
            col.push(setCol("Código", "codigo", "codigo", 10));
            col.push(setCol("Nombre", "nombre", "nombre", 20));
            col.push(setCol("Banda", "banda", "banda", 20, r => r || "no tiene"));
            col.push(setCol("Asaltos", "asaltos", "asaltos", 20, r => r.length));
            break;
        case "asaltos": 
            col.push(setCol("Código", "codigo", "codigo", 10));
            col.push(setCol("Fecha asalto", "fecha", "fecha", 16));        
            col.push(setCol("Sucursal", "sucursal", "sucursal", 16, r => r || "no tiene"));
            col.push(setCol("Juez", "juez", "juez", 16, r => r || "no tiene"));
            col.push(setCol("Detenido", "detenido", "detenido", 16, r => r || "no tiene"));
            col.push(setCol("Fecha condena", "fechaCondena", "fechaCondena", 16, r => r || "no tiene"));        
            break;
        default:
            break;
    }
    return col;
}

function Todos (props) {    

    return (        
        <> 
            {props.datos.length != 0 ?
            <>
                <h1>Listado de {props.entidad}</h1>
                <Table 
                    columns = {setHeader(props.entidad)}
                    pagination={{position: ["bottomRight", "topRight"],}}
                    dataSource = {props.datos.map (
                        (item, index) => (
                            {
                                ...item,
                                key: index
                            }
                        )
                    )
                }
                />                                        
                <h3>Total de {props.entidad}: {props.datos.length}</h3>
            </>        
            :
            <h1>No hay {props.entidad} </h1>
            }
        </>
    )
}

export default Todos;