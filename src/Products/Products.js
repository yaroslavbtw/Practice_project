import React from 'react';
import axios from 'axios';
import {Table} from "reactstrap";
import '../App/App.css';
import './Products.css';
import Modal from "../Modal/Modal";

export default class Products extends React.Component {
    state = {
        products: []
    }

    componentDidMount() {
        axios.get(`http://localhost:8080/api/v1/products/`)
            .then(res => {
                const products = res.data;
                this.setState({ products });
            })
    }


    render() {
        return (
            <div className='App'>
                <h3 className='App'>Products</h3>
                <Table bordered hover responsive>
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Calories</th>
                        <th>Proteins</th>
                        <th>Fats</th>
                        <th>Carbohydrates</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.products.map(item => (
                        <tr key={item.uuid}>
                            <td>{item.title}</td>
                            <td>{item.calories}</td>
                            <td>{item.proteins.toFixed(2)}</td>
                            <td>{item.fats.toFixed(2)}</td>
                            <td>{item.carbohydrates.toFixed(2)}</td>
                            <td><Modal data={{
                                text: "Editing of the record ",
                                type: "primary",
                                name: "Edit",
                                value: item
                            }}/></td>
                            <td><Modal data={{
                                text: "Do you want to delete the record?",
                                type: "danger",
                                name: "Delete",
                                value: item
                            }}/></td>
                        </tr>
                    ))}

                    </tbody>
                </Table>
            </div>
        );
    }
}