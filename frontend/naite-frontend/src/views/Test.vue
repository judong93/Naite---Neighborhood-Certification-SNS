<template>
  <div id='test'>
    <div id="a">a</div>
    <div id="b">b</div>
    <div id="c">c</div>
    
    <div id="d">d</div>
    <div id="e">e</div>
    <div id="f">f</div>
    <div id="g">g</div>
    <div id="h">h</div>
    <div id="i">i</div>
    <div id="j">j</div>

  </div>
</template>
<script>

export default {
  name: 'TestColor',

  components: {

  }
}
</script>
<style>
#test {
  position: relative;
  width: 100vw;
  display:flex
}
#test > div {
  width: 10%;
  height: 100vh;
  }
  

#a {
  background-color:#3F9F47;
  margin-right: 10px;
}
#b {
  background-color:#6BB664;
  margin-right: 10px;
}
#c {
  background-color: #5EB656;
  margin-right: 10px;
}
#d {
  background-color:#3F9F47;
  margin-right: 10px;
}
#e {
  background-color:#2D7533;
  margin-right: 10px;
}
#f {
  background-color:#A87A4F;
  margin-right: 10px;
}

#g {
  background-color:#3F9F47;
  margin-right: 10px;
}
#h {
  /* background-color:#2D7533; */
  margin-right: 10px;
}
#i {
  /* background-color:#2D7533; */
  margin-right: 10px;
}
#j {
  /* background-color:#2D7533; */
  margin-right: 10px;
}


</style>