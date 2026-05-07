import RateYo from "rateyo";
import "rateyo/lib/es/rateyo.css";

const ratingElement = document.getElementById("rateYo");

if (ratingElement) {
  new RateYo(ratingElement, {
    rating: 3,
    numStars: 5,
    spacing: "10px",
    maxValue: 5,
    normalFill: "gray",
    ratedFill: "gold",
  });
}
