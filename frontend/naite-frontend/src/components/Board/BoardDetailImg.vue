<template>
    <div id='boarddetailimg'>               
        <img :src="boardImg[0]" alt="" style='max-width:250px' class='webImg'>
        <img :src="boardImg[0]" alt="" style='max-width:100px' class='mobileImg'>
        <div v-if='boardImg[1]'>
            <div>
                <img :src="boardImg[1]" alt="" style='margin-top:10%;margin-left:10%'>
                <div class='hoveredBox'>{{boardImg.length-1}}장 더보기...</div>
            </div>

        </div>
    </div>
</template>
<script>
export default {
    name:'BoardDetailImg',
    props:{
        boardImg:[Object,Array]
    },
    methods:{
        loadingImg:function(){
            if (this.boardImg.length>1){  
                let firstImg =document.querySelector('.webImg')
                if (screen.width < 501) {
                    firstImg =document.querySelector('.mobileImg')
                }
                const secondImg = document.querySelector('#boarddetailimg > div > div >img')
                const hoberdImg = document.querySelector('.hoveredBox')
                
                secondImg.style.width = `${firstImg.clientWidth/2}px`
                hoberdImg.style.width = `${firstImg.clientWidth/2}px`
                hoberdImg.style.height = `${secondImg.clientHeight}px`
                hoberdImg.style.bottom ='0px'

                const paddingTop = secondImg.clientHeight/2 - secondImg.clientHeight*0.1
                hoberdImg.style.paddingTop = `${paddingTop}px`
                hoberdImg.style.color = 'black'
                hoberdImg.style.fontFamily='font1'                
            }
        }
    },
    watch:{
        boardImg:function(){
            setTimeout(() => {                
                this.loadingImg()
            }, 100);
        }
    },
    mounted(){
        setTimeout(() => {                
                this.loadingImg()
        }, 100);
    },
    updated(){
        setTimeout(() => {                
                this.loadingImg()
            }, 100);
    },
    created(){
        setTimeout(() => {                
                this.loadingImg()
            }, 100);
    }
    
}
</script>
<style>
#boarddetailimg{
    position:relative;
    width: 100%;
    background-color: teal;
    padding: 1%;
    display: flex;
    align-items: flex-end;    

    
}

#boarddetailimg > img :nth-child(1) {
    position: relative;
    left:0;
    top:0;   
    cursor:pointer; 
}
.mobileImg{
    display:none;
}
#boarddetailimg > div > div{
    position: relative;
    cursor:pointer;
}


.hoveredBox {
    position:absolute;
    background-color: rgba(255, 255, 255, 0.459);    
    top:0;    
    margin-top: 10%;
    margin-left: 10%;
    padding-top: auto;
    cursor:pointer;

}


@media screen and (max-width:501px) {
    
    .webImg {
        display: none;
    }
    .mobileImg {
        display:initial;
    }
    .hoveredBox{
        font-size: 10px;
    }
        
}
</style>