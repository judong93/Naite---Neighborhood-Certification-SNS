<template>
  <div id="sign">
    <div class='bgColor'></div>
    <Login @changeSignup='changeSignup' :toLogin='toLogin' @switchFindLogin='switchFindLogin' :toFind='toFind' />
    <Signup @changeLogin='changeLogin' :toSignup='toSignup'/>
    <FindSign :toFind='toFind' @switchFindLogin='switchFindLogin' />
    <!-- <div id='signlogo'> 
      <img src="../../assets/naitelogo.png" alt="">
    </div> -->
  </div>
</template>

<script>
import Login from '@/components/Sign/Login'
import Signup from '@/components/Sign/Signup'
import FindSign from '@/components/Sign/FindSign'



export default {
    name:'Sign',
    components:{
        Login,
        Signup,
        FindSign,
    },
    data: function() {
        return {
          toSignup:false,
          toLogin:false,
          toFind:false,
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
      },
      switchFindLogin:function(){
        if (this.toFind) {
          this.toLogin = true
          this.toFind = false
        } else {
          this.toLogin = false
          this.toFind = true
        }
      },
    },
    watch:{
      toSignup: function(){
        const screenWidth = window.screen.width
        const bg = document.querySelector('.bgColor')
        const sign =document.querySelector('#sign')
        if (this.toSignup && screenWidth<501) {
          bg.style.height = '160vh'
          sign.style.overflow='auto'
        } else {
          bg.style.height = '100vh'
          sign.style.overflow='hidden'
        }
      }
    }
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
  background-size: 100vw 100vh;
  height: 100vh;   
  width:100vw;
  /* overflow:hidden;  */
  overflow:auto;
  
}

.bgColor {
  height: 100vh;   
  width:100vw;
  background-color: rgba(0, 0, 0, 0.7);
  margin:0;

}

@media screen and (max-width: 501px) {
  #sign {    
    background: url('../../assets/m_sign.jpg') no-repeat center center;
    overflow: hidden;
  }

  .bgColor {
    background-color: rgba(0, 0, 0, 0.2);   
  }
}




</style>
