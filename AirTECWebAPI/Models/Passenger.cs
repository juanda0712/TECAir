using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Passenger
{
    public int Idpassenger { get; set; }

    public string Fullname { get; set; } = null!;

    public int? Phonenumber { get; set; }

    public virtual ICollection<Suitcase> Suitcases { get; set; } = new List<Suitcase>();

    public virtual ICollection<Ticket> Tickets { get; set; } = new List<Ticket>();

    public virtual ICollection<User> Users { get; set; } = new List<User>();
}
