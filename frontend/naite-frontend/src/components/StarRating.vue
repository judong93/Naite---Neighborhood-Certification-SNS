<template>
  <div class="star-widget">
    <div class="star-box">
      <input @click="rateOne" type="radio" name="rate" id="rate-5">
      <label for="rate-5" class="fas fa-star"></label>
      <input @click="rateTwo" type="radio" name="rate" id="rate-4">
      <label for="rate-4" class="fas fa-star"></label>
      <input @click="rateThree" type="radio" name="rate" id="rate-3">
      <label for="rate-3" class="fas fa-star"></label>
      <input @click="rateFour" type="radio" name="rate" id="rate-2">
      <label for="rate-2" class="fas fa-star"></label>
      <input @click="rateFive" type="radio" name="rate" id="rate-1">
      <label for="rate-1" class="fas fa-star"></label>
    </div>
  </div>

</template>

<script>
export default {
  name: 'StarRating',
  data: function () {
    return {
      ratedStar: 0,
    }
  },
  methods: {
    rateOne: function () {
      this.ratedStar = 1
    },
    rateTwo: function () {
      this.ratedStar = 2
    },
    rateThree: function () {
      this.ratedStar = 3
    },
    rateFour: function () {
      this.ratedStar = 4
    },
    rateFive: function () {
      this.ratedStar = 5 
    }
  }
}
</script>

<style>
.star-widget {
  display: flex;
  justify-content: center;
}
.star-box input {
  display: none;
}
.star-box label {
  font-size: 30px;
  color: #444;
  padding: 0 5px;
  float: right;
  transition: all 0.2s ease;
  margin-bottom: 0;
}
input:not(:checked) ~ label:hover,
input:not(:checked) ~ label:hover ~ label{
  color: #fd4;
}
input:checked ~ label{
  color: #fd4;
}
input#rate-5:checked ~ label{
  color: #fd4;
  text-shadow: 0 0 20px #952;
}
.fa-star {
  padding: 0;
}

</style>