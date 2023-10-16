using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Student
{
    public int UniversityCard { get; set; }

    public string UniversityName { get; set; } = null!;

    public int? Miles { get; set; }

    public int? Iduser { get; set; }

    public virtual User? IduserNavigation { get; set; }
}
