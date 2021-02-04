<template>
  <div id="sign">
    <div class='bgColor'></div>
    <Login @changeSignup='changeSignup' :toLogin='toLogin'/>
    <Signup @changeLogin='changeLogin' :toSignup='toSignup'/>

  </div>
</template>

<script>
import Login from '@/components/Sign/Login'
import Signup from '@/components/Sign/Signup'



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
    background: url('../../assets/sign.jpg') no-repeat center center;
    background-size: 100vw 100vh;
    height: 100vh;   
    width:100vw;
    overflow:hidden; 
  
}

.bgColor {
  width:1000%;
  height:1000%;
  background-color: rgba(0, 0, 0, 0.7);

}

/* @media screen and (max-width: 1800px) {
  #sign {
    overflow: auto;
  }
} */

</style>
