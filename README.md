# service config 差异化

## local 以TCIntegrationService为例
>### TCIntegrationService



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