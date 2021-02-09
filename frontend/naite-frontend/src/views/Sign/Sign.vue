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


@keyframes fadeout {
    from {
        opacity: 1;
        visibility: visible;
        
    } 
    to {
        opacity: 0;
        visibility: hidden;
        
    }
}

@keyframes fadein {
    from {
        opacity: 0;
        visibility: hidden;
        
    }
    to {
        opacity: 1;
        visibility: visible;
        
    }
}



#sign {
    position:relative;
    background: url('../../assets/sign.jpg') no-repeat center center;
    background-size: 1920px 969px;
    height: 969px;   
    width:1920px;
    overflow:hidden; 
    /* overflow:auto; */
  
}

.bgColor {
  height: 969px;   
  width:1920px;
  background-color: rgba(0, 0, 0, 0.7);
  margin:0;

}

@media screen and (max-width: 1800px) {
  #sign {
    overflow: auto;
  }
}

</style>
