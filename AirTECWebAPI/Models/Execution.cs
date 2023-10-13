using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Execution
{
    public int Idexecution { get; set; }

    public int? NumberFlight { get; set; }

    public string? PlateNumber { get; set; }

    public DateOnly? Date { get; set; }

    public TimeOnly? DepartureTime { get; set; }

    public int? Price { get; set; }

    public string? Status { get; set; }

    public int? BoardingDoor { get; set; }

    public virtual Flight? NumberFlightNavigation { get; set; }

    public virtual Plane? PlateNumberNavigation { get; set; }

    public virtual ICollection<Promotion> Promotions { get; set; } = new List<Promotion>();

    public virtual ICollection<Reservation> Reservations { get; set; } = new List<Reservation>();

    public virtual ICollection<Seat> Seats { get; set; } = new List<Seat>();

    public virtual ICollection<Ticket> Tickets { get; set; } = new List<Ticket>();
}
