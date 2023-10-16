export class Airport {
  Name?: string;
  City?: string;
  FlightDestinationNavigations: Flight[] = [];
  FlightOriginNavigations: Flight[] = [];
  LayoverDestinationNavigations: Layover[] = [];
  LayoverOriginNavigations: Layover[] = [];
}

export class Flight {

  // Define las propiedades de la clase Flight si es necesario
}

export class Layover {

  // Define las propiedades de la clase Layover si es necesario
}
