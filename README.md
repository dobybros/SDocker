# service config 差异化
###### 注释：当对某个service的config.properties进行改变时，需在下面“local”中纪录变化，不熟上线后下面的变化将有部署人员删除，下面以TCIntegrationService为例子：
## local
>### TCIntegrationService(注：这只是个例子)
        (添加)imapi.host=http://imapi.aculearn.com/rest/acuim/
        (添加)imapi.privatekey=6472ac46-5b83-4471-8cd3-a76a7b5610b2
        (添加)imapi.apikey=5ad5e354f8d7041e0f53bdd7
        (添加)tcapi.host=http://tcapi.aculearn.com/rest/acutuition/
        (添加)tcapi.privatekey=d62e7948-b581-49aa-af8c-bdc7f7376c97
        (添加)tcapi.apikey=5aa0c95ff8d7041b7494384b


## cn_bj1
>### TCIntegrationService
        "_id" : "tcintegration",
	    "imapi_host" : "http://localhost/rest/acuim/",
	    "imapi_privatekey" : "374dea8f-b13d-496b-bea6-f2728f2d9917",
	    "imapi_apikey" : "5abcf073371fcf255c08cd66",
	    "tcapi_host" : "http://localhost/rest/acutuition/",
	    "tcapi_privatekey" : "093de59d-58e0-4398-ab4b-0c13b5491811",
	    "tcapi_apikey" : "5abcfe29371fcf255c08cd6e"

>### IMAPIGatewayWeb
        "_id" : "acuim",
        "im_login_host" : "http://localhost:10052",
        "remote_service_host" : "localhost:80"
    
>### TCAPIGatewayWeb
        "_id" : "acutuition",
        "enter_playback_url" : "https://class.aculearn.cn/#/enterplay/",
        "enter_class_url" : "https://class.aculearn.cn/#/enterclass/"
    
>### GWTuitionRoomService
        "_id" : "gwtuitionroom",
        "remote_service_host" : "localhost:80"
    
>### TCClassroomService
        "_id" : "tcclassroom",
        "zookeeper_host" : "localhost:2181"