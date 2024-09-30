import { useNavigate, useParams, redirect } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { Button, Checkbox, Input, Form, message} from "antd"
import serv from "../../services/serv"
import { AuthContext } from "../../components/AuthContext";

function Login() {

    const {login, login2, logout} = useContext(AuthContext);

    const navigate = useNavigate(); 
    const [formLogin] = Form.useForm();

    const onFinish = async (values) => {
        /*
        console.log('Success:', values);
        //let token = await serv.login();
        let token = "jaja";        
        if (token != null) {
            console.log("entra acá?");

            login();
            //localStorage.setItem("nicastillo.prog2", token);
            //console.log("token es:")
            //console.log(localStorage.getItem("nicastillo.prog2"));
            navigate("/");
            //return redirect("/usuarios");
        }
        */
        try {
            await login2(values.usr, values.pwd);            
            formLogin.resetFields();
            navigate("/");
        }
        catch (err) {
            message.error("Usuario o contraseña inválida");
        }
    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);        
    };

    /*
    useEffect (() => {
        navigate("/");
    },[]);
    */
    
    return (
        <>
            <h1>Iniciar sesión</h1>
            <Form
                form = {formLogin}
                name = "basic"
                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                style={{
                    maxWidth: 600,
                }}
                initialValues={{
                    remember: true,
                }}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete="off"
            >
                <Form.Item name = "usr" label = {<b>Usuario</b>}
                    rules={[{
                        required: true,
                        message: "Usuario requerido."
                    }]}
                >
                    <Input
                        placeholder="Ingrese usuario"
                        style={{ width: 200 }}
                    />
                </Form.Item>
                <Form.Item name = "pwd" label = {<b>Clave</b>}
                    rules={[{
                        required: true,
                        message: "Clave requerida."
                    }]}
                >
                    <Input.Password
                        placeholder="Ingrese clave"
                        style={{ width: 200 }}
                    />
                </Form.Item>
                <Form.Item
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}
                >
                    <Button type="primary" htmlType="submit">
                        Enviar
                    </Button>
                </Form.Item>
            </Form>
        </>
    );
}

export default Login;
