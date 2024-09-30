import {createContext, useEffect, useState} from "react";
import jwt_decode from "jwt-decode";
import serv from "../services/serv";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const [autenticado, setAutenticado] = useState( () => {
        const token =  localStorage.getItem("nicastillo.prog2");
        return !!token;
    })

    const [rol, setRol] = useState( () => {
        const token = localStorage.getItem("nicastillo.rol");
        return token;
    });

    const checkAuth = () => {
        const token = localStorage.getItem("nicastillo.prog2");
        const token2 = localStorage.getItem("nicastillo.rol");
        setAutenticado(!!token);
        setRol(token2);
    }

    async function login2 (u, p) {
        try {
            const token = await serv.login(u,p);
            localStorage.setItem("nicastillo.prog2",token);
            //const decoded = jwt_decode(token);
            setAutenticado(true);
        }
        catch (e) {            
            console.log(e.message)
            throw new Error();
        }
    }

    const login = (token) => {
        //const res = await serv.login();
        localStorage.setItem("nicastillo.prog2", token);
        const x = prompt("rol?");
        localStorage.setItem("nicastillo.rol",x);
        setAutenticado(true);
        setRol(x);
    } 

    const logout = () => {
        localStorage.removeItem("nicastillo.prog2");
        //localStorage.removeItem("nicastillo.rol");
        //alert("logout");
        setAutenticado(false);
        //setRol("");
    }

    const esVigi = () => {
        if (autenticado) {
            const decoded = jwt_decode(localStorage.getItem("nicastillo.prog2"));
            const rol = decoded.rol;
            return rol === "Vigilante";
        }        
    }

    const esInves = () => {
        if (autenticado) {
            const decoded = jwt_decode(localStorage.getItem("nicastillo.prog2"));
            const rol = decoded.rol;
            return rol === "Investigador";
        }
    }

    const esAdmin = () => {
        if (autenticado) {
            const decoded = jwt_decode(localStorage.getItem("nicastillo.prog2"));
            const rol = decoded.rol;
            return rol === "Administrador";
        }
    }
    
    function getUserName () {
        if (autenticado) {
            const decoded = jwt_decode(localStorage.getItem("nicastillo.prog2"));
            return decoded.sub;
        }
    }

    function getRol () {
        if (autenticado) {
            const decoded = jwt_decode(localStorage.getItem("nicastillo.prog2"));
            return decoded.rol;
        }
    }

    function getServicios () {
        if (getRol() === "Vigilante") {
            const decoded = jwt_decode(localStorage.getItem("nicastillo.prog2"));  
            //console.log(decoded.contratos);
            //const a = JSON.parse(decoded.contratos);
            //console.log(a);
            //return decoded.contratos;
            console.log(JSON.parse(decoded.contratos));
            return JSON.parse(decoded.contratos);
        }
    }



    useEffect( () => {
        checkAuth();

        const handleStorageChange = () => {
            checkAuth();
        };
        window.addEventListener("storage", handleStorageChange);        
        return () => {
            window.removeEventListener("storage", handleStorageChange);
        };
    }, []);

    
    return (
        <AuthContext.Provider value = {{
            autenticado, 
            rol, 
            esVigi, 
            esInves, 
            esAdmin, 
            getUserName,
            getRol,
            getServicios,
            login, 
            login2, 
            logout}}>
            {children}
        </AuthContext.Provider>
    );
}

