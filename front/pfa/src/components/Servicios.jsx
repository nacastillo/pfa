import {Table} from "antd";

function setCol (t, di, k, w, r) {
    return {
        title: t,
        dataIndex: di,
        key: k,
        width: w,
        render: r
    }
}

function setHeader () {
    let col = [];
    col.push(setCol("ID", "id", "id", 10));
    col.push(setCol("Codigo", "codigo", "codigo", 10)); // codigo
    col.push(setCol("Fecha", "fecha", "fecha", 10)); // fecha
    col.push(setCol("ID Sucursal", "sucursal", "sucursal", 10, r => r || "no tiene")); // sucursal
    col.push(setCol("Sucursal", "nombreSucursal", "nombreSucursal", 10, r => r || "no tiene")); // sucursal
    col.push(setCol("Armado", "armado", "armado", 10, r => r? "sí": "no")); // armado
    return col;
}

function Servicios (props)  { // contratos
    return (
        <>
            {props.datos.length > 0 ?
            <>
                <h1>Mis servicios</h1>
                <Table
                    columns = {setHeader()}
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
                >
                </Table>
                <h3>Total de servicios: {props.datos.length}</h3>
            </>
            :
            <h1>Usted no tiene servicios asignados</h1>
        }
        </>
    )
}

export default Servicios;


