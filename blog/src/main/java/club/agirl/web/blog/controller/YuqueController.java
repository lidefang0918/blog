package club.agirl.web.blog.controller;

import club.agirl.web.blog.bean.TocTree;
import club.agirl.web.blog.bean.yuque.*;
import club.agirl.web.blog.service.YuqueQueryService;
import club.agirl.web.blog.bean.DocDetail;
import club.agirl.web.blog.bean.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "yuque")
@Slf4j
@CacheConfig(cacheNames = "yuque")
public class YuqueController {
    private static final int DEFAULT_GROUP_ID = 541616;
    private static final int BOOK_DEFAULT_DEPTH = 1;
    @Autowired
    private YuqueQueryService queryService;

    @PostMapping(value = "callback")
    public Result<DocDetail> callback(@RequestBody JSONObject response) {
//        String src = "{\"data\":{\"id\":2537065,\"slug\":\"ixz7yn\",\"title\":\"容器\",\"book_id\":378995,\"book\":{\"id\":378995,\"type\":\"Book\",\"slug\":\"notes\",\"name\":\"我的速记\",\"user_id\":236732,\"description\":\"随时随地写笔记\",\"creator_id\":0,\"public\":0,\"items_count\":5,\"likes_count\":0,\"watches_count\":0,\"content_updated_at\":\"2019-10-18T16:21:35.462Z\",\"updated_at\":\"2019-10-18T16:21:35.000Z\",\"created_at\":\"2019-07-18T12:10:57.000Z\",\"_serializer\":\"v2.book\"},\"user_id\":236732,\"user\":{\"id\":236732,\"type\":\"User\",\"login\":\"yaomo\",\"name\":\"妖魔\",\"avatar_url\":\"https://gw.alipayobjects.com/zos/rmsportal/wYnHWSXDmBhiEmuwXsym.png\",\"large_avatar_url\":\"https://gw.alipayobjects.com/zos/rmsportal/wYnHWSXDmBhiEmuwXsym.png?x-oss-process=image/resize,m_fill,w_320,h_320\",\"medium_avatar_url\":\"https://gw.alipayobjects.com/zos/rmsportal/wYnHWSXDmBhiEmuwXsym.png?x-oss-process=image/resize,m_fill,w_160,h_160\",\"small_avatar_url\":\"https://gw.alipayobjects.com/zos/rmsportal/wYnHWSXDmBhiEmuwXsym.png?x-oss-process=image/resize,m_fill,w_80,h_80\",\"books_count\":5,\"public_books_count\":3,\"followers_count\":0,\"following_count\":1,\"created_at\":\"2019-01-03T09:20:39.000Z\",\"updated_at\":\"2019-09-30T01:00:05.000Z\",\"_serializer\":\"v2.user\"},\"format\":\"lake\",\"body\":\"- [ Linux 容器]当我们在谈容器的时候,我们谈的是什么？\\n- Docker 在当下很火,那么,当我们谈 Docker ,谈容器的时候,我们在谈什么?\\n- 或者说,你对 Docker ,对容器了解吗?容器,到底是怎么一回事儿?\\n\\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/236732/1571384273466-7dc65f19-23d8-4026-98ac-25e4e5f5d476.png#align=left&display=inline&height=180&name=image.png&originHeight=180&originWidth=150&search=&size=19633&status=done&width=150)\\n\\n这篇文章着重来讲一下 Linux 容器,为什么强调 Linux 容器,而不是 Docker ,是因为 Docker 是基于虚拟化技术来实现的,但是这篇文章涉及到 Linux 容器的核心实现方面,两者不同,所以着重强调一下.\\n\\n容器其实是一种沙盒技术.顾名思义,沙盒就是能够像一个集装箱一样,把你的应用装起来.这样,应用与应用之间就有了边界而不会相互干扰;同时装在沙盒里面的应用,也可以很方便的被搬来搬去,这也是 PaaS 想要的最理想的状态.\\n\\n但是说起来容易,等到真正实现起来的时候,就会有难度.<br />因为容器是运行在宿主机上面的,当它运行起来的时候,需要加载到内存中,需要 CPU 完成加法操作等等.也就是说,如果想要实现真正意义上的容器,就要解决这样的问题,但现实中这样的问题还没办法解决.\\n\\n那么我们所说的容器,又是说什么呢?容器的核心功能又是什么呢?\\n\\n其实容器的核心功能,就是通过约束和修改进程的动态表现,从而创造出一个\\\"边界\\\".对于 Docker 等大多数 Linux 容器来说, Cgroup 技术是用来制造约束的主要手段,而 Namespace 技术则是用来修改进程视图的主要方法.\\n\\n我们都知道,进程在静态状态下就是程序,只是磁盘上的二进制文件.而当它运行起来时,才成为进程.所以,当我们开始运行程序时,操作系统都会为进程分配一个进程编号,这个编号就是进程的唯一标识.假设我们开始运行了一个程序,它的 PID=100 .也就是说这个程序是第 100 个进程,在它前面还有 99 个进程.而现在,如果我们通过 Docker 把这个程序运行在一个容器当中,那么 Docker 就会在第 100 个进程创建时,给它施一个\\\"障眼法\\\",让它永远看不到其他 99 个进程,这样这个程序就会误以为自己是第 1 个进程这种机制,其实就是对被隔离应用的进程空间做了手脚,使得这些进程只能看到重新计算过的进程编号,比如上面的第 100 个进程,经过 Docker 的\\\"障眼法\\\"之后,误以为自己是第 1 个进程,但是实际上在宿主机的操作系统中,它还是原来的第 100 个进程.<br />而这种技术,就是 Linux 里的 Namespace 机制.<br />这种机制,使得容器隔离成为了可能.\\n\\n谈完隔离之后,就需要来谈限制.\\n\\nLinux Cgroups 的全称是 Linux Control Group .它最主要的作用,就是限制一个进程组能够使用的资源上限,包括 CPU ,内存,磁盘,网络带宽等. Cgroups 的每一项子系统都有其独有的资源限制能力,比如:<br />blkio :为块设备设定I/O限制,一般用于磁盘等设备;<br />cpuset :为进程分配单独的CPU核和对应的内存节点;<br />memory :为进程设定内存使用的限制;<br />Linux Cgroups 的设计还是比较易用的,它就是一个子系统目录加上一组资源限制文件的组合.对于 Docker 等 Linux 容器项目来说,它们只需要在每个子系统下面,为每个容器创建一个控制组(即创建一个新目录),然后在启动容器进程之后,把这个进程的 PID 填写到对应控制组的 tasks 文件中就可以了.\\n\\n至于在这些控制组下面的资源文件里填什么值,就由用户执行 docker run 时的参数指定.\\n\\n容器,这个所谓很高大上的东西,到最后,也只是操作系统上的一个特殊进程而已.\\n\\n所以,容器本身并没有价值,有价值的是\\\"容器编排\\\",<br />当我们在谈容器的时候,其实是在谈如何更好的去编排容器.而这也是为什么当下 k8s 这么火的原因.\\n\",\"body_draft\":\"- [ Linux 容器]当我们在谈容器的时候,我们谈的是什么？\\n- Docker 在当下很火,那么,当我们谈 Docker ,谈容器的时候,我们在谈什么?\\n- 或者说,你对 Docker ,对容器了解吗?容器,到底是怎么一回事儿?\\n\\n![image.png](https://cdn.nlark.com/yuque/0/2019/png/236732/1571384273466-7dc65f19-23d8-4026-98ac-25e4e5f5d476.png#align=left&display=inline&height=180&name=image.png&originHeight=180&originWidth=150&search=&size=19633&status=done&width=150)\\n\\n这篇文章着重来讲一下 Linux 容器,为什么强调 Linux 容器,而不是 Docker ,是因为 Docker 是基于虚拟化技术来实现的,但是这篇文章涉及到 Linux 容器的核心实现方面,两者不同,所以着重强调一下.\\n\\n容器其实是一种沙盒技术.顾名思义,沙盒就是能够像一个集装箱一样,把你的应用装起来.这样,应用与应用之间就有了边界而不会相互干扰;同时装在沙盒里面的应用,也可以很方便的被搬来搬去,这也是 PaaS 想要的最理想的状态.\\n\\n但是说起来容易,等到真正实现起来的时候,就会有难度.<br />因为容器是运行在宿主机上面的,当它运行起来的时候,需要加载到内存中,需要 CPU 完成加法操作等等.也就是说,如果想要实现真正意义上的容器,就要解决这样的问题,但现实中这样的问题还没办法解决.\\n\\n那么我们所说的容器,又是说什么呢?容器的核心功能又是什么呢?\\n\\n其实容器的核心功能,就是通过约束和修改进程的动态表现,从而创造出一个\\\"边界\\\".对于 Docker 等大多数 Linux 容器来说, Cgroup 技术是用来制造约束的主要手段,而 Namespace 技术则是用来修改进程视图的主要方法.\\n\\n我们都知道,进程在静态状态下就是程序,只是磁盘上的二进制文件.而当它运行起来时,才成为进程.所以,当我们开始运行程序时,操作系统都会为进程分配一个进程编号,这个编号就是进程的唯一标识.假设我们开始运行了一个程序,它的 PID=100 .也就是说这个程序是第 100 个进程,在它前面还有 99 个进程.而现在,如果我们通过 Docker 把这个程序运行在一个容器当中,那么 Docker 就会在第 100 个进程创建时,给它施一个\\\"障眼法\\\",让它永远看不到其他 99 个进程,这样这个程序就会误以为自己是第 1 个进程这种机制,其实就是对被隔离应用的进程空间做了手脚,使得这些进程只能看到重新计算过的进程编号,比如上面的第 100 个进程,经过 Docker 的\\\"障眼法\\\"之后,误以为自己是第 1 个进程,但是实际上在宿主机的操作系统中,它还是原来的第 100 个进程.<br />而这种技术,就是 Linux 里的 Namespace 机制.<br />这种机制,使得容器隔离成为了可能.\\n\\n谈完隔离之后,就需要来谈限制.\\n\\nLinux Cgroups 的全称是 Linux Control Group .它最主要的作用,就是限制一个进程组能够使用的资源上限,包括 CPU ,内存,磁盘,网络带宽等. Cgroups 的每一项子系统都有其独有的资源限制能力,比如:<br />blkio :为块设备设定I/O限制,一般用于磁盘等设备;<br />cpuset :为进程分配单独的CPU核和对应的内存节点;<br />memory :为进程设定内存使用的限制;<br />Linux Cgroups 的设计还是比较易用的,它就是一个子系统目录加上一组资源限制文件的组合.对于 Docker 等 Linux 容器项目来说,它们只需要在每个子系统下面,为每个容器创建一个控制组(即创建一个新目录),然后在启动容器进程之后,把这个进程的 PID 填写到对应控制组的 tasks 文件中就可以了.\\n\\n至于在这些控制组下面的资源文件里填什么值,就由用户执行 docker run 时的参数指定.\\n\\n容器,这个所谓很高大上的东西,到最后,也只是操作系统上的一个特殊进程而已.\\n\\n所以,容器本身并没有价值,有价值的是\\\"容器编排\\\",<br />当我们在谈容器的时候,其实是在谈如何更好的去编排容器.而这也是为什么当下 k8s 这么火的原因.\\n\",\"body_html\":\"<!doctype html><div class=\\\"lake-content-editor-core lake-engine\\\" data-lake-element=\\\"root\\\" data-selection-236732=\\\"%7B%22path%22%3A%5B%5B31%2C0%2C25%5D%2C%5B31%2C0%2C25%5D%5D%2C%22uuid%22%3A%22236732%22%2C%22active%22%3Atrue%7D\\\"><ul lake-indent=\\\"0\\\" style=\\\"list-style-type: disc; margin: 0px; padding-left: 23px; font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word;\\\"><li>[ Linux 容器]当我们在谈容器的时候,我们谈的是什么？</li><li>Docker 在当下很火,那么,当我们谈 Docker ,谈容器的时候,我们在谈什么?</li><li>或者说,你对 Docker ,对容器了解吗?容器,到底是怎么一回事儿?</li></ul><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><span data-card-type=\\\"inline\\\" data-lake-card=\\\"image\\\"><img data-role=\\\"image\\\" src=\\\"https://cdn.nlark.com/yuque/0/2019/png/236732/1571384273466-7dc65f19-23d8-4026-98ac-25e4e5f5d476.png\\\" data-raw-src=\\\"https://cdn.nlark.com/yuque/0/2019/png/236732/1571384273466-7dc65f19-23d8-4026-98ac-25e4e5f5d476.png\\\" class=\\\"image lake-drag-image\\\" alt=\\\"image.png\\\" title=\\\"image.png\\\" style=\\\"visibility: visible; width: 150px; height: 180px;\\\"></span></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">这篇文章着重来讲一下 Linux 容器,为什么强调 Linux 容器,而不是 Docker ,是因为 Docker 是基于虚拟化技术来实现的,但是这篇文章涉及到 Linux 容器的核心实现方面,两者不同,所以着重强调一下.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">容器其实是一种沙盒技术.顾名思义,沙盒就是能够像一个集装箱一样,把你的应用装起来.这样,应用与应用之间就有了边界而不会相互干扰;同时装在沙盒里面的应用,也可以很方便的被搬来搬去,这也是 PaaS 想要的最理想的状态.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">但是说起来容易,等到真正实现起来的时候,就会有难度.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">因为容器是运行在宿主机上面的,当它运行起来的时候,需要加载到内存中,需要 CPU 完成加法操作等等.也就是说,如果想要实现真正意义上的容器,就要解决这样的问题,但现实中这样的问题还没办法解决.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">那么我们所说的容器,又是说什么呢?容器的核心功能又是什么呢?</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">其实容器的核心功能,就是通过约束和修改进程的动态表现,从而创造出一个\\\"边界\\\".对于 Docker 等大多数 Linux 容器来说, Cgroup 技术是用来制造约束的主要手段,而 Namespace 技术则是用来修改进程视图的主要方法.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">我们都知道,进程在静态状态下就是程序,只是磁盘上的二进制文件.而当它运行起来时,才成为进程.所以,当我们开始运行程序时,操作系统都会为进程分配一个进程编号,这个编号就是进程的唯一标识.假设我们开始运行了一个程序,它的 PID=100 .也就是说这个程序是第 100 个进程,在它前面还有 99 个进程.而现在,如果我们通过 Docker 把这个程序运行在一个容器当中,那么 Docker 就会在第 100 个进程创建时,给它施一个\\\"障眼法\\\",让它永远看不到其他 99 个进程,这样这个程序就会误以为自己是第 1 个进程这种机制,其实就是对被隔离应用的进程空间做了手脚,使得这些进程只能看到重新计算过的进程编号,比如上面的第 100 个进程,经过 Docker 的\\\"障眼法\\\"之后,误以为自己是第 1 个进程,但是实际上在宿主机的操作系统中,它还是原来的第 100 个进程.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">而这种技术,就是 Linux 里的 Namespace 机制.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">这种机制,使得容器隔离成为了可能.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">谈完隔离之后,就需要来谈限制.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">Linux Cgroups 的全称是 Linux Control Group .它最主要的作用,就是限制一个进程组能够使用的资源上限,包括 CPU ,内存,磁盘,网络带宽等. Cgroups 的每一项子系统都有其独有的资源限制能力,比如:</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">blkio :为块设备设定I/O限制,一般用于磁盘等设备;</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">cpuset :为进程分配单独的CPU核和对应的内存节点;</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">memory :为进程设定内存使用的限制;</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">Linux Cgroups 的设计还是比较易用的,它就是一个子系统目录加上一组资源限制文件的组合.对于 Docker 等 Linux 容器项目来说,它们只需要在每个子系统下面,为每个容器创建一个控制组(即创建一个新目录),然后在启动容器进程之后,把这个进程的 PID 填写到对应控制组的 tasks 文件中就可以了.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">至于在这些控制组下面的资源文件里填什么值,就由用户执行 docker run 时的参数指定.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">容器,这个所谓很高大上的东西,到最后,也只是操作系统上的一个特殊进程而已.</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\"><br></p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">所以,容器本身并没有价值,有价值的是\\\"容器编排\\\",</p><p style=\\\"font-size: 14px; color: rgb(38, 38, 38); line-height: 24px; letter-spacing: 0.05em; outline-style: none; overflow-wrap: break-word; margin: 0px;\\\">当我们在谈容器的时候,其实是在谈如何更好的去编排容器.而这也是为什么当下 k8s 这么火的原因.</p></div>\",\"public\":1,\"status\":1,\"likes_count\":0,\"comments_count\":0,\"content_updated_at\":\"2019-10-18T16:21:35.000Z\",\"created_at\":\"2019-09-03T13:37:33.000Z\",\"updated_at\":\"2019-10-18T16:21:35.000Z\",\"published_at\":\"2019-10-18T16:21:35.000Z\",\"first_published_at\":\"2019-09-03T13:40:17.000Z\",\"word_count\":1151,\"_serializer\":\"webhook.doc_detail\",\"action_type\":\"update\",\"publish\":false,\"path\":\"yaomo/notes/ixz7yn\"}}";
//        response = JSON.parseObject(src);
        if (response == null || response.isEmpty()) {
            return Result.fail(400, "callback content is empty.");
        }

        DocDetailBean data = JSON.parseObject(response.getString("data"), DocDetailBean.class);
        if (data == null) {
            return Result.fail(400, "callback content is empty.");
        }

        log.info("callback success ===> docId={}, bookId={}, title={}, actionType={}, publish={}",
                data.getId(),data.getBookId(), data.getTitle(), data.getActionType(), data.isPublish());
        return Result.ok(DocDetail.of(data));
    }

    @GetMapping(value = "tree")
    @Cacheable
    public Result getTree(){
        log.info("Get Tree begin ... ");
        List<BookBean> groupBooks = queryService.findGroupBooksSortByIdAcs(DEFAULT_GROUP_ID);

        List<TocTree> tocTrees = new ArrayList<>();
        // toc
        for (BookBean bookBean : groupBooks) {
            int bookId = bookBean.getId();
            TocTree bookTree = TocTree.builder()
                    .name(bookBean.getName())
                    .bookId(bookId)
                    .depth(BOOK_DEFAULT_DEPTH)
                    .build();
            if (bookBean.getItemsCount()>0) {
                bookTree.setChildren(getDocTrees(bookId));
            }
            tocTrees.add(bookTree);
        }
        log.info("Get Tree success");
        // docs
        return Result.ok(tocTrees);
    }

    private List<TocTree> getDocTrees(int bookId) {
        List<DocBean> docs = this.queryService.findBookAllDocs(bookId);
        Map<String, DocBean> docMap = new HashMap<>(docs.size());
        docs.forEach(doc -> docMap.put(doc.getSlug(), doc));

        List<TocBean> bookToc = this.queryService.getBookToc(bookId);
        List<TocTree> docTrees = new ArrayList<>();
        Stack<TocTree> stack = new Stack<>();
        for (TocBean tocBean : bookToc) {
            Integer docId = Optional.ofNullable(docMap.get(tocBean.getSlug())).map(DocBean::getId).orElse(null);
            TocTree docTree = TocTree.builder()
                    .name(tocBean.getTitle())
                    .bookId(bookId)
                    .docId(docId)
                    .depth(tocBean.getDepth() + BOOK_DEFAULT_DEPTH)
                    .build();

            TocTree parent = stack.empty() ? docTree : stack.peek();
            if (docTree.getDepth() <= parent.getDepth()){
                while (true) {
                    if (stack.empty()){
                        break;
                    }
                    parent = stack.pop();
                    if (parent.getDepth() < docTree.getDepth()) {
                        if(CollectionUtils.isEmpty(parent.getChildren())){
                            parent.setChildren(new ArrayList<>());
                        }
                        parent.getChildren().add(docTree);
                        break;
                    }
                }
            }else{
                if(CollectionUtils.isEmpty(parent.getChildren())){
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(docTree);
            }

            if (docTree.getDepth()<=2){
                docTrees.add(docTree);
            }

            stack.push(docTree);
        }
        return docTrees;
    }


    @GetMapping(value="books/{bookId}/docs/{docId}")
    @Cacheable
    public Result<DocDetail> getDoc(@PathVariable("bookId") Integer bookId, @PathVariable("docId") Integer docId){
        log.info("Get Doc begin ===> bookId={}, docId={}",bookId, docId);
        Optional<DocDetailBean> docDetail = this.queryService.getDocDetail(bookId, docId);
        log.info("Get Doc success ===> id={}, tile={}, de",docId, docDetail.map(DocDetailBean::getTitle).get());
        return docDetail.map(detailBean -> Result.ok(DocDetail.of(detailBean))).orElseGet(Result::ok);
    }
}
