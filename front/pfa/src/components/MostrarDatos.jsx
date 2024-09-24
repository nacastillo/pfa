function MostrarDatos (props) {
    /**
     * obj: objeto (entidad) a mostrar
     * entidad:
     */

    let comp = {};
    switch (props.entidad) {
        case "asalto": 
            <>                    
                Codigo: <b>{props.obj.codigo}</b> <br/>
                Fecha: <b>{props.obj.fecha}</b> <br/>
                {props.obj.sucursal ? 
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
                <br/>
            </>
            break;

        default:
            break;
    }
    

    return (
        <>
            {( props.entidad === "banda"
            || props.entidad === "sucursal"
            || props.entidad === "entidad"
            ) ? 
            <h2>Datos de la {props.entidad}</h2>
            : 
            <h2>Datos del {props.entidad}</h2>
            }
            {comp}
        </>
    )

}

export default MostrarDatos