import { useNavigate} from "react-router-dom";
import { useEffect} from "react";

function Logout() {
    const nav = useNavigate();
    localStorage.removeItem("nicastillo.prog2");    
    //nav("/login");    

    useEffect (() => {
        nav("/login");
    },[]);

}

export default Logout