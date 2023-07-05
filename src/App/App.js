import './App.css';
import Products from "../Products/Products";
import Header from "../Header/Header";
import React from "react";
import Footer from "../Footer/Footer";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Auth from "../Auth/Auth";

// export default function CustomRoutes() {
//     return (
//
//     );
// }
function App() {
    return (
        <div className="App">
            <Header/>
            <BrowserRouter>
                 <Routes>
                     <Route path="/products" element={<Products />}/>
                     <Route path="/auth" element={<Auth />} />
                     <Route path="/register" element={<Auth />} />
                </Routes>
            </BrowserRouter>
            <Footer/>
        </div>
    )
}

export default App;
