import axios from "axios"

//const dir = "localhost";
const dir = "192.168.0.142";

const api = axios.create({
        //baseURL: "http://localhost:2000",
        baseURL: `http://${dir}:2000`,
        timeout: 1000 * 7, // 7 segundos,        
    }
)

api.interceptors.request.use (
    (conf) => {
        //console.log("Enviando solicitud.");
        const token = localStorage.getItem("nicastillo.prog2");
        //console.log("token es:\n" + token);
        if (token) {
            conf.headers["Authorization"] = token;
            //conf.headers.Authorization = token;            
        }
        return conf;
    },
    (err) => Promise.reject(err)
)

api.interceptors.response.use (
    (res) => res.data,
    //(err) => Promise.reject(console.log(err))    
    (err) => Promise.reject(err)
)

export default api