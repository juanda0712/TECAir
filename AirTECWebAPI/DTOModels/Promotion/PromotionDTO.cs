namespace AirTECWebAPI.DTOModels.Promotion
{
    public class PromotionDTO
    {
        public int Number { get; set; }
        public int? Idexecution { get; set; }
        public string? Period { get; set; }
        public int? Price { get; set; }

        public PromotionDTO() { }

        public PromotionDTO(int number, int? idexecution, string? period , int? price)
        {
            Number = number;
            Idexecution = idexecution;
            Period = period;
            Price = price;
        }
    }
}
