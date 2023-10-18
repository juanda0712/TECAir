type TimeOnly = string;
//revisar el tipo de TimeOnly
export class Execution {
    idexecution?: number;
    numberFlight?: number;
    plateNumber?: string;
    date?: Date;
    departureTime?: TimeOnly;
    price?: number;
    status?: string;
    boardingDoor?: number;
}

export class Promotion {
    number?: number;
    idexecution?: number;
    period?: string;
    price?: number;
}

export class Reservation {
    idreservation?: number;
    iduser?: number;
    idexecution?: number;
}

export class Seat {
    seatNumber?: number;
    idexecution?: number;
}

export class Ticket {
    idticket?: number;
    taxes?: number;
    totalAmount?: number;
    seatNumber?: number;
    idexecution?: number;
    idpassenger?: number;
    iduser?: number;
}
