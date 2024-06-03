import Swal from "sweetalert2";

export const successAlert = (text) => {
    Swal.fire({
    title: 'Success!',
    text: text,
    icon: 'success',
    confirmButtonText: 'OK'
    });
};

export const errorAlert = (text) => {
    Swal.fire({
    title: 'Error!',
    text: text,
    icon: 'error',
    confirmButtonText: 'OK'
    });
};