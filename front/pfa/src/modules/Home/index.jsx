function Home() {

    return (
        <>
            <h1>Home</h1>
            <span style={{ textAlign: "justify" }}>
                <u>Consigna</u>: desarrolle un sistema para gestionar la información de la Policía Federal sobre la seguridad en algunas entidades bancarias, teniendo en cuenta que:
                <ul>
                    <li>Cada entidad bancaria se caracteriza por un código y por el domicilio de su Central.</li>
                    <li>Cada entidad bancaria tiene más de una sucursal que también se caracteriza por un código y por el domicilio, así como por el número de empleados de dicha sucursal.</li>
                    <li>Cada sucursal contrata, según el día, algunos vigilantes, que se caracterizan por un código y su edad. Un vigilante puede ser contratado por diferentes sucursales (incluso de diferentes entidades), en distintas fechas y es un dato de interés dicha fecha, así como si se ha contratado con arma o no.</li>
                    <li>Por otra parte, se quiere controlar a las personas que han sido detenidas por asaltar las sucursales de dichas entidades. Estas personas se definen por una clave (código) y su nombre completo.</li>
                    <li>Alguna de estas personas están integradas en algunas bandas organizadas y por ello se desea saber a qué banda pertenecen, sin ser de interés si la banda ha participado en el delito o no. Dichas bandas se definen por un número de banda y por el número de miembros.</li>
                    <li>Asimismo, es interesante saber en qué fecha ha asaltado cada persona una sucursal. Evidentemente, una persona puede asaltar varias sucursales en diferentes fechas, así como una sucursal puede ser asaltada por varias personas.</li>
                    <li>Igualmente, se quiere saber qué juez ha estado encargado del caso, sabiendo que un individuo, por diferentes delitos, puede ser juzgado por diferentes jueces. Es de interés saber, en cada delito, si la persona detenida ha sido condenada o no, y de haberlo sido, cuánto tiempo pasó o pasará en la cárcel. Un juez se caracteriza por una clave interna del juzgado, su nombre y los años de servicio.</li>
                    <li>En ningún caso interesa saber si un vigilante ha participado en la detención de un asaltante.</li>
                    <li>Al sistema podrán acceder tres tipos de usuarios: 
                        <ul>
                            <li>vigilantes (que sólo podrán consultar sus datos)</li> 
                            <li>investigadores (que podrán consultar todo)</li>
                            <li>administradores (que administrarán todo)</li>
                        </ul>
                    </li>
                </ul>
            </span>
        </>
    )
}

export default Home