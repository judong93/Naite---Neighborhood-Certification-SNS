<template>
  <div id="app">
    <!-- <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link> -->
    <!-- </div> -->
    <span class="firstpage-welcome-text">자취생들을 위한 커뮤니티 어서옵쇼라잇</span>
    <img src="./assets/firstpage.jpg" alt="">
    <button>START</button>
    <router-view/>
  </div>
</template>

<script>
</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  
}


#nav a.router-link-exact-active {
  /* color: #42b983; */
  color: font-color
}

.firstpage-welcome-text {
  position: absolute;
  top: 100px;
  left:50%;
  height:100px;
  color: black;
  z-index:1;

}


#app > img {
  position:absolute;
  top:0;
  left:0;
  height: 100vh;
  width:100vw;
}

#app > button {
  position:absolute;
  background-color: orange;
  top:50%;
  left:50%;
  transform: translate(-50%,-50%)
}


</style>
