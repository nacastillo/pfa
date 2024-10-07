import axios from "axios"

const dir = "localhost";

const api = axios.create({
        //baseURL: "http://localhost:2000",
        baseURL: `http://${dir}:2000`,
        timeout: 1000 * 7, // 7 segundos,        
    }
)

api.interceptors.request.use (
    (conf) => {        
        const token = localStorage.getItem("nicastillo.prog2");        
        if (token) {
            conf.headers["Authorization"] = token;
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