import {useContext} from "react";
import { Route, Routes, BrowserRouter } from "react-router-dom"
import Home from "./modules/Home"
import Layout from "./modules/Layout";
import Login from "./modules/Login";
import Logout from "./modules/Logout";
import Usuarios from "./modules/Usuarios";
import Sucursales from "./modules/Sucursales";
import Jueces from "./modules/Jueces";
import Bandas from "./modules/Bandas";
import Contratos from "./modules/Contratos";
import Entidades from "./modules/Entidades";
import Asaltos from "./modules/Asaltos";
import Detenidos from "./modules/Detenidos";
import NotFound from "./modules/NotFound";

import { AuthContext } from "./components/AuthContext"

function App() {    

    const {autenticado} = useContext(AuthContext);

    return (        
        <div className="App">            
            <BrowserRouter>
                <Routes>
                    <Route element={<Layout />}>
                        <>
                            <Route path = "/" element={<Home />} />                        
                            {autenticado ?
                            <>                                
                                <Route path = "usuarios/:modo?" element= {<Usuarios />} />
                                <Route path = "sucursales/:modo?" element= {<Sucursales />}/>
                                <Route path = "jueces/:modo?" element= {<Jueces />} />
                                <Route path = "bandas/:modo?" element= {<Bandas />} />
                                <Route path = "contratos/:modo?" element= {<Contratos />} />
                                <Route path = "entidades/:modo?" element= {<Entidades />} />
                                <Route path = "asaltos/:modo?" element= {<Asaltos />} />
                                <Route path = "detenidos/:modo?" element= {<Detenidos />} />
                                <Route path = "logout" element = {<Logout />} />
                            </>
                            :
                            <Route path = "login" element = {<Login />} />
                            }
                        <Route path = "*" element= {<NotFound />}/>
                        </>
                    </Route>
                </Routes>
            </BrowserRouter>        
        </div>
    )
}

export default App
