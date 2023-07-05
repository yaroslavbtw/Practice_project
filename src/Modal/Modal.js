import React, { useState } from 'react';
import { Form, Row, Col, FormGroup, Input, Label, Button, Modal as ReactModal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import axios from "axios";

function Modal({data}) {
    const [modal, setModal] = useState(false);

    const [title, setTitle] = useState('');
    const [calories, setCalories] = useState('');
    const [proteins, setProteins] = useState('');
    const [fats, setFats] = useState('');
    const [carbohydrates, setCarbohydrates] = useState('');

    const handleTitleChange = (event) => {
        setTitle(event.target.value);
    };

    const handleCaloriesChange = (event) => {
        setCalories(event.target.value);
    };

    const handleProteinsChange = (event) => {
        setProteins(event.target.value);
    };

    const handleFatsChange = (event) => {
        setFats(event.target.value);
    };

    const handleCarbohydratesChange = (event) => {
        setCarbohydrates(event.target.value);
    };

    const toggle = () => setModal(!modal);

    const EditProduct = (uuid, data) =>{
        axios.put(`http://localhost:8080/api/v1/products/`.concat(uuid), data)
            .then(res => {
                const statusDt = res.data;
                console.log(statusDt)
            })
        toggle();
    }

    const DeleteProduct = (uuid) =>{
        axios.delete(`http://localhost:8080/api/v1/products/`.concat(uuid))
            .then(res => {
                const statusDt = res.data;
                console.log(statusDt)
            })
        toggle();
    }

    const { text, type, name, value} = data;

    if(name === "Edit")
        return (
            <div>
                <Button color={type} onClick={toggle}>
                    {name}
                </Button>
                <ReactModal isOpen={modal} toggle={toggle}>
                    <ModalHeader toggle={toggle}>Confirm action</ModalHeader>
                    <ModalBody>
                        <Form>
                            <Row>
                                <Col md={6}>
                                    <FormGroup>
                                        <Label for="titleId">
                                            Title
                                        </Label>
                                        <Input
                                            id="titleId"
                                            name="title"
                                            type="text"
                                            placeholder={value.title}
                                            onChange={handleTitleChange}
                                        />
                                    </FormGroup>
                                </Col>
                                <Col md={6}>
                                    <FormGroup>
                                        <Label for="caloriesId">
                                            Calories
                                        </Label>
                                        <Input
                                            id="caloriesId"
                                            name="calories"
                                            type="number"
                                            placeholder={value.calories}
                                            onChange={handleCaloriesChange}
                                        />
                                    </FormGroup>
                                </Col>
                            </Row>
                            <Row>
                                <Col md={6}>
                                    <FormGroup>
                                        <Label for="proteinsId">
                                            Proteins
                                        </Label>
                                        <Input
                                            id="proteinsId"
                                            name="proteins"
                                            type="number"
                                            placeholder={value.proteins}
                                            onChange={handleProteinsChange}
                                        />
                                    </FormGroup>
                                </Col>
                                <Col md={6}>
                                    <FormGroup>
                                        <Label for="fatsId">
                                            Fats
                                        </Label>
                                        <Input
                                            id="fatsId"
                                            name="fats"
                                            type="number"
                                            placeholder={value.fats}
                                            onChange={handleFatsChange}
                                        />
                                    </FormGroup>
                                </Col>
                            </Row>
                            <FormGroup>
                                <Label for="carbohydratesId">
                                    Carbohydrates
                                </Label>
                                <Input
                                    id="carbohydratesId"
                                    name="carbohydrates"
                                    type="number"
                                    placeholder={value.carbohydrates}
                                    onChange={handleCarbohydratesChange}
                                />
                            </FormGroup>
                        </Form>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary" onClick={event => {
                            event.preventDefault();
                            EditProduct(value.uuid, {
                                "title": title,
                                "calories": calories,
                                "proteins": proteins,
                                "fats": fats,
                                "carbohydrates": carbohydrates,
                            })
                        }}>
                            {name}
                        </Button>
                        <Button color="secondary" onClick={toggle}>
                            Cancel
                        </Button>
                    </ModalFooter>
                </ReactModal>
            </div>
        );
    else{
        return (
            <div>
                <Button color={type} onClick={toggle}>
                    {name}
                </Button>
                <ReactModal isOpen={modal} toggle={toggle}>
                    <ModalHeader toggle={toggle}>Confirm action</ModalHeader>
                    <ModalBody>
                        {text}
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary" onClick={event => {
                            event.preventDefault();
                            DeleteProduct(value.uuid)}}>
                            {name}
                        </Button>
                        <Button color="secondary" onClick={toggle}>
                            Cancel
                        </Button>
                    </ModalFooter>
                </ReactModal>
            </div>
        );
    }
}

export default Modal;