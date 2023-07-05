import React, {useState} from "react";
import {Button, Form, FormGroup, Input, Label} from "reactstrap";
import './Auth.css'
import axios from "axios";
import { redirect } from 'react-router-dom';

export default function Auth(){
    const [redirectToProducts, setRedirectToProducts] = useState(false);
    const [mail, setMail] = useState('');
    const [password, setPassword] = useState('');

    const handleMailChange = (event) => {
        setMail(event.target.value);
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };
    const handleSubmit = () =>{
        axios.post(`http://localhost:8080/api/v1/account/login`, {
            "mail": mail,
            "password": password
        })
            .then(res => {
                const token = res.data.token;
                if (token)
                    setRedirectToProducts(true);
            })
            .catch((err) => {
                console.log(err);
            });
    }
    if (redirectToProducts) {
        return redirect("/products");
    }

    return(
        <div className="form-container">
            <div className="container">
                    <Form onSubmit={handleSubmit}>
                        <FormGroup floating>
                            <Input
                                id="exampleEmail"
                                name="email"
                                placeholder="Email"
                                type="email"
                                onChange={handleMailChange}
                            />
                            <Label for="exampleEmail">
                                Email
                            </Label>
                        </FormGroup>
                        <FormGroup floating>
                            <Input
                                id="examplePassword"
                                name="password"
                                placeholder="Password"
                                type="password"
                                onChange={handlePasswordChange}
                            />
                            <Label for="examplePassword">
                                Password
                            </Label>
                        </FormGroup>
                        <FormGroup check>
                            <Input type="checkbox" />
                            <Label check>
                                Check me out
                            </Label>
                        </FormGroup>
                        <Button>
                            Submit
                        </Button>
                    </Form>
            </div>
        </div>
    );
}