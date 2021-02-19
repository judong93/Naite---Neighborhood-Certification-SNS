<template>
    <div id='marketcategory'>
        <div class="marketShowCategory" @click='toggleMenu(0)'>공동구매</div>
        <div class="marketAll"  @click='toggleMenu(1)'>중고거래</div>
    </div>
</template>
<script>
import _ from 'lodash'


export default {
    name:'MarketCategory',
    data:function(){
        return{
            toggled:false,  
            selectedIdx:0,                     
        }
    },
    methods:{
        toggleMenu:function(num){
            let btnColor = ['#3569a5','#00c8fc','#fcba27','#dd4b39','#f7e600','#2db400','#A87A4F']
            if (this.toggled){
                this.selectedIdx = num
                const categorys = document.querySelectorAll('#marketcategory > div')
                let i = 0
                categorys.forEach(category => {                                                            
                    category.style.visibility='hidden'
                    if (i===this.selectedIdx){
                        let selectedNum = _.random(0,btnColor.length-1)
                        category.style.backgroundColor = btnColor[selectedNum] 
                        category.style.visibility='visible'   
                    } else {
                        category.style.backgroundColor = 'transparent'
                    }
                    category.style.left = '0%'                     
                    i+=1
                });
                this.toggled=false
            } else {
                const categorys = document.querySelectorAll('#marketcategory > div')
                let i = 0
                categorys.forEach(category => {                    
                    let selectedNum = _.random(0,btnColor.length-1)
                    const left = i*120                    
                    category.style.visibility='visible'
                    category.style.backgroundColor = btnColor[selectedNum]                    
                    category.style.left = `${left}px` 
                    btnColor.splice(selectedNum,1)                    
                    i+=1
                });
                this.toggled=true
            }
        },

    },
    watch:{
        selectedIdx:function(){
            this.$emit('changeSmallCategory',this.selectedIdx+6)
        }
    }
}
</script>

<style>
#marketcategory {
    position: absolute;
    top:24%;
    left:20.5%;    
    color:white;
    text-align: center;
    display: flex;
}

#marketcategory > div {
    position:absolute;
    height:30px;
    border-radius: 10px;
    transition:0.3s;
    width: 100px;
    background-color: transparent;
    visibility: hidden;    
    left:0;
    cursor:pointer;
    opacity: 0.8;
}


#marketcategory span {
    font-family: 'Font Awesome 5 Free';
    font-weight: 900;
}


#marketcategory > div:nth-of-type(1){
    background-color: #66ec4b; 
    visibility: visible;
}

#marketcategory > div:hover {
    opacity: 1;
    width: 120px ;    
}



@media screen and (max-width:501px) {
    #marketcategory{
        display:none
    }
    
}

</style>