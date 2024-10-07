import { useContext, useState } from "react";
import { Outlet, Link } from "react-router-dom";
import { Layout, Menu } from "antd";
import {
    BankOutlined,    
    CalendarOutlined,
    EnvironmentOutlined,
    HomeOutlined,
    LoginOutlined,
    LogoutOutlined,
    MehOutlined,    
    TeamOutlined,
    TruckOutlined
} from '@ant-design/icons'

import { AuthContext } from "../../components/AuthContext";

const { Content, Footer, Sider } = Layout

const contenedor = {
    position: "relative",
    minHeight: "100vh",    
};

const siderStyle = {
    color: '#FAFAFA',
    backgroundColor: '#364479',
};

const footerStyle = {
    position: "absolute",
    bottom: "0",
    width: "100%",
    height: "5rem",
    textAlign: 'center',
    color: '#FAFAFA',
    backgroundColor: '#000000',
};

const wrapper = {
    paddingBottom: "5rem",
    flex: 1,
    display: "flex",
    flexDirection: "row"
}

function getItem(label, key, icon, children) { // getItem devuelve un objeto para pasarlo por "items"
    return {
        key,
        icon,
        children,
        label,
    }
}

const rootSubmenuKeys = ['1', '2', '3', '4', '5', '6', '7', '8', '9']

function App() {
    const [openKeys, setOpenKeys] = useState([])        
    const {autenticado, rol, esVigi, esInves, esAdmin, getUserName, getRol, login, logout} = useContext(AuthContext);
    const onOpenChange = (keys) => {
        const latestOpenKey = keys.find((key) => openKeys.indexOf(key) === -1);
        if (latestOpenKey && rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
            setOpenKeys(keys);
        } else {
            setOpenKeys(latestOpenKey ? [latestOpenKey] : []);
        }
    }    

    const itemsState = [        
        getItem(<Link to="/"> Home </Link>, '1', <HomeOutlined />),
        autenticado && (esVigi() ? 
            getItem(<Link to = "/usuarios/misservicios">Mis servicios</Link>, "123", <CalendarOutlined />) 
            : 
            getItem(<Link> Usuarios </Link>, '2', <TeamOutlined />, [        
                getItem(<Link to="/usuarios"> Ver todos </Link>, "20"),
                esAdmin() && getItem(<Link to="/usuarios/nuevo"> Nuevo </Link>, "21"),
                getItem(<Link to="/usuarios/buscar"> Buscar </Link>, "22")
            ])),        
        autenticado && (esInves() || esAdmin()) &&
            getItem(<Link> Sucursales </Link>, '3', <EnvironmentOutlined />, [ 
                getItem(<Link to="/sucursales"> Ver todas </Link>, "30"),
                esAdmin() && getItem(<Link to="/sucursales/nuevo"> Nueva </Link>, "31"),
                getItem(<Link to="/sucursales/buscar"> Buscar </Link>, "32")
            ]),
        autenticado && (esInves() || esAdmin()) &&
            getItem(<Link> Jueces </Link>, '4', <TeamOutlined />, [
                getItem(<Link to="/jueces"> Ver todos </Link>, "40"),
                esAdmin() && getItem(<Link to="/jueces/nuevo"> Nuevo </Link>, "41"),
                getItem(<Link to="/jueces/buscar"> Buscar </Link>, "42")
            ]),
        autenticado && (esInves() || esAdmin()) &&            
            getItem(<Link> Bandas </Link>, '5', <TeamOutlined />, [   
                getItem(<Link to="/bandas"> Ver todas </Link>, "50"),     
                esAdmin() && getItem(<Link to="/bandas/nuevo"> Nueva </Link>, "51"),
                getItem(<Link to="/bandas/buscar"> Buscar </Link>, "52")
            ]),
        autenticado && (esInves() || esAdmin()) &&
            getItem(<Link> Contratos </Link>, '6', <CalendarOutlined />, [        
                getItem(<Link to="/contratos"> Ver todos </Link>, "60"),
                esAdmin() && getItem(<Link to="/contratos/nuevo"> Nuevo </Link>, "61"),
                getItem(<Link to="/contratos/buscar"> Buscar </Link>, "62")
            ]),
        autenticado && (esInves() || esAdmin()) &&
            getItem(<Link> Entidades </Link>, '7', <BankOutlined />, [        
                getItem(<Link to="/entidades"> Ver todas </Link>, "70"),     
                esAdmin() && getItem(<Link to="/entidades/nuevo"> Nueva </Link>, "71"),
                getItem(<Link to="/entidades/buscar"> Buscar </Link>, "72")
            ]),
        autenticado && (esInves() || esAdmin()) &&
            getItem(<Link> Asaltos </Link>, '8', <TruckOutlined />, [        
                getItem(<Link to="/asaltos"> Ver todos </Link>, "80"),     
                esAdmin() && getItem(<Link to="/asaltos/nuevo"> Nuevo </Link>, "81"),
                getItem(<Link to="/asaltos/buscar"> Buscar </Link>, "82")
            ]),
        autenticado && (esInves() || esAdmin()) &&            
            getItem(<Link> Detenidos </Link>, '9', <MehOutlined />, [        
                getItem(<Link to="/detenidos"> Ver todos </Link>, "90"),     
                esAdmin() && getItem(<Link to="/detenidos/nuevo"> Nuevo </Link>, "91"),
                getItem(<Link to="/detenidos/buscar"> Buscar </Link>, "92")
            ]),    
        !autenticado &&
            getItem(<Link to= "/login">Iniciar sesión</Link>, '0',<LoginOutlined />),
        autenticado && 
            getItem(<Link to= "/logout"><span style = {{color: "red"}}>Cerrar sesión</span></Link>, '111', <LogoutOutlined />)
    ];     

    return (
        <Layout style={contenedor}> {/* contenedor */}
            <Layout style={wrapper}> {/* wrapper */}
                <Sider width="200" style={siderStyle}>
                    <img style = {{display: "block", marginTop: "25px", marginLeft: "auto", marginRight: "auto"}} src = "/escudo.png"></img>
                    <h1 style = {{textAlign: "center"}}>
                        Policía Federal<br/> Argentina
                    </h1>
                    <span style = {{display: "flex", justifyContent: "center"}}>
                        {autenticado? 
                            <>{getUserName()} - {getRol()} </>
                            :
                            <>sin autenticar</>
                        }
                    </span>
                    <Menu 
                        //style = {{fontFamily:"sans-serif" , color: "#0000FF"}} 
                        defaultSelectedKeys={['1']} 
                        openKeys={openKeys}
                        onOpenChange={onOpenChange} 
                        mode="inline" 
                        // items={items2}
                        items = {itemsState}
                    >                        
                    </Menu>
                </Sider>
                <Content style={{ padding: "20px" }}>
                    <Outlet />
                </Content>
            </Layout>
            <Footer style={footerStyle}>
                <h1>
                    {new Date().getFullYear()} - INSPT - UTN
                </h1>
            </Footer>
        </Layout>
    )
}

export default App;