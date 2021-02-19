<template>
  <div id="how-to-use">
    <div class="how-to-use-button"><i class="fas fa-chevron-circle-right fa-2x"></i></div>
    <div class="how-to-use-content">d</div>
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
    top:  9.2%;
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
    background-color: rgb(152, 196, 193);
    width: 100%;
    height: 100%;
    z-index: ;
    /* border-radius: 3%; */
    /* border: 3px lightgreen solid; */
  }
</style>