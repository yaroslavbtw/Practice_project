import React from 'react';
import {
    Collapse,
    Navbar,
    NavbarToggler,
    NavbarBrand,
    Nav,
    NavItem,
    NavLink} from 'reactstrap';

export default class Header extends React.Component {
    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.state = {
            isOpen: false
        };
    }
    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }
    render() {
        return (
            <div>
                <Navbar color="light" light expand="md">
                    <NavbarBrand href="/products">ReactApp</NavbarBrand>
                    <NavbarToggler onClick={this.toggle} />
                    <Collapse isOpen={this.state.isOpen} navbar>
                        <Nav className="ml-auto" navbar>
                            <NavItem>
                                <NavLink href="/recipes/">Recipes</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/products/">Products</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="https://github.com/yaroslavbtw">GitHub</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/auth/">Log in</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/register/">Sign in</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/logout/">Logout</NavLink>
                            </NavItem>
                        </Nav>
                    </Collapse>
                </Navbar>
            </div>
        );
    }
}
