<template>
  <div id="how-to-use">
    <div class="how-to-use-button"><i class="fas fa-chevron-circle-right fa-2x"></i></div>
    <div class="how-to-use-content"></div>
  </div>
</template>

<script>
export default {
  name: 'HowToUse'
}
</script>

<style>
  #how-to-use {
    position: absolute;
    top:  9.5%;
    width: 18%;
    height: 91%;
    left: 0;
  }
  .how-to-use-button{
    position: absolute;
    top: 40%;
    left: 0;
    height: 5%;
    width: 15%;
    overflow: hidden;
  }
  .how-to-use-content {
    position: absolute;
    width: 100%;
    height: 100%;
    border: 3px lightgreen solid;
  }
</style>