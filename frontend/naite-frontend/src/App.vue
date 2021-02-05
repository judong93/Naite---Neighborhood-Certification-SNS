<template>
  <div id="app">
    <Navbar v-if='signNav' />
    <router-view/>
    <Message v-if='signNav' />
  </div>
</template>
<script>
import Navbar from '@/components/Basic/Navbar'
import Message from '@/components/Basic/Message'

export default {
  name:'app',
  components:{
    Navbar,
    Message,
  },
  data: function(){
    return {
      signNav: false,
    }
  },
  methods:{
    showing:function(){
      const jwt = localStorage.getItem('jwt')
      if (jwt) {
        this.signNav = true
      } else if (!jwt) {
        this.signNav = false
      }
    },
    token:function(){
      const jwt = localStorage.getItem('jwt')
      if (jwt&&window.location.pathname==='/sign') {
        this.$router.push({name:'MainBoard'})
      } else if (jwt&&window.location.pathname==='/'){
        this.$router.push({name:'MainBoard'})
      } else if (!jwt && window.location.pathname !=='/sign') {
        this.$router.push({name:'Sign'})
      }
    }
  },
  updated:function(){
    this.token()
    this.showing()
    
  },
  created:function(){
    this.token()
    this.showing()
  },
}

</script>
<style>
@font-face {
    font-family: font1;
    src: url(./fonts/titleli.ttf) format('truetype');
}


#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

</style>
