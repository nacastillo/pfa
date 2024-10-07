export function esAdmin () {
    return false;
}

export function esVigi () {
    return true;
}

export function estaLoggeado () {
    return localStorage.getItem("nicastillo.prog2");
}