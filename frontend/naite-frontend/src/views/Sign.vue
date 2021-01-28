<template>
  <div id="sign">
    <Login @changeSignup='changeSignup' :toLogin='toLogin'/>
    <Signup @changeLogin='changeLogin' :toSignup='toSignup'/>

  </div>
</template>

<script>
import Login from '@/components/Login'
import Signup from '@/components/Signup'



export default {
    name:'Sign',
    components:{
        Login,
        Signup,
    },
    data: function() {
        return {
          toSignup:false,
          toLogin:false,
        }
    },
    methods:{
      changeSignup:function(){
        this.toSignup = true
        this.toLogin=false
      },
      changeLogin:function(){
        this.toLogin = true
        this.toSignup = false
      }
    },
}
</script>



<style>
#sign {
    position:relative;
    background: url('../assets/sign.jpg') no-repeat center center;
    height: 100vh;   
    width:100vw;
    overflow:hidden; 
  
}

@media screen and (max-width: 1800px) {
  #sign {
    overflow: auto;
  }
}

</style>
