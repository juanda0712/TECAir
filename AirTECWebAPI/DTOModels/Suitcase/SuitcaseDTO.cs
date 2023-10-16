using AirTECWebAPI.Models;
using System.Drawing;
using static System.Runtime.InteropServices.JavaScript.JSType;
using AirTECWebAPI.Models;
using System.Drawing;

namespace AirTECWebAPI.DTOModels.Suitcase
{
    public class SuitcaseDTO
    {
        public int Idsuitcase { get; set; }
        public int Weight { get; set; }
        public string? Color { get; set; }
        public int? Idpassenger { get; set; }

        public SuitcaseDTO()
        {
        }

        public SuitcaseDTO(int idsuitcase, int weight, string? color, int? idpassenger)
        {
            Idsuitcase = idsuitcase;
            Weight = weight;
            Color = color;
            Idpassenger = idpassenger;
        }
    }
}
