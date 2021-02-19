<template>
  <div class="category-bar">
    <div @click="selectAll" class="category-all"> </div>
    <div @click="selectGroupBuying" class="group-buying-button"></div>
    <div @click="selectSecondhand" class="secondhand-market"></div>
    <button @click="transformCategoryBar" class="category-button">클릭</button>
  </div>
</template>

<script>
export default {
  name: "CategoryBar",
  data: function () {
    return {
      categoryBarStatus: false,
    }
  },
  methods:{
    transformCategoryBar: function () {
      this.categoryBarStatus=!this.categoryBarStatus
      const bar = document.getElementsByClassName('category-bar')
      if (this.categoryBarStatus) {
          bar.style.height='0px'

      } 
    },
    selectAll: function () {

    },
    selectGroupBuying: function () {

    },
    selectSecondhand: function () {

    },
  },
}
</script>

<style>
.category-bar {
  position: absolute;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  overflow: hidden;
  border: lightgray solid 2px;
  border-radius: 5%;
  height: 250px;
  width: 60px;
  left: 3%;
  top: 350px;
  display: none;
}
.category-bar > div {
  cursor: pointer;
  overflow: hidden;
  background-image: url('../assets/cha2.png');
  background-size: 100%;
  background-position: center;
  width: 32px;
  height: 32px;
  border-radius: 100%;
}
.category-all {
  background-color: red;
}
.group-buying-button {
  background-color: lightgreen;
}
.secondhand-market {
  background-color: lightpink;
}
.category-button {
  height: 32px;
}
</style>