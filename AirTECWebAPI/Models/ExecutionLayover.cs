namespace AirTECWebAPI.Models;

public partial class ExecutionLayover
{
    public int Idlayover { get; set; }

    public int Idexecution { get; set; }

    public int Price { get; set; }

    public virtual Layover? LayoverNavigation { get; set; }
    public virtual Execution? ExecutionNavigation { get; set; }
}
