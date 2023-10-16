using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class User
{
    public int Iduser { get; set; }

    public string Fullname { get; set; } = null!;

    public int? Phonenumber { get; set; }

    public string Password { get; set; } = null!;

    public string Email { get; set; } = null!;

    public int? Idpassenger { get; set; }

    public virtual Passenger? IdpassengerNavigation { get; set; }

    public virtual ICollection<Reservation> Reservations { get; set; } = new List<Reservation>();

    public virtual ICollection<Student> Students { get; set; } = new List<Student>();

    public virtual ICollection<Ticket> Tickets { get; set; } = new List<Ticket>();
}
