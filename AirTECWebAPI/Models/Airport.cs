using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Airport
{
    public string Name { get; set; } = null!;

    public string? City { get; set; }

    public virtual ICollection<Flight> FlightDestinationNavigations { get; set; } = new List<Flight>();

    public virtual ICollection<Flight> FlightOriginNavigations { get; set; } = new List<Flight>();

    public virtual ICollection<Layover> LayoverDestinationNavigations { get; set; } = new List<Layover>();

    public virtual ICollection<Layover> LayoverOriginNavigations { get; set; } = new List<Layover>();
}
