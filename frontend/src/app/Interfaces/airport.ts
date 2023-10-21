export class Airport {
  name?: string;
  city?: string;
}

export class Flight {
  number?: number;
  origin?: string;
  destination?: string;
}

export class Layover {
  idlayover?: number;
  numberFlight?: number;
  origin?: string;
  destination?: string;
}

export class LoginRequest {
  email?: String;
  password?: String;
}
