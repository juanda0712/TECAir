export class Passenger {

    idpassenger?: number;
    fullname?: string;
    phonenumber?: number;
}

export class Suitcase {
    idsuitCase?: number;
    weight?: number;
    color?: string;
    idpassenger?: number;
}
export class User {

    iduser?: number;
    fullname?: string;
    phonenumber?: number;
    password?: string;
    email?: string;
    idpassenger?: number;
}