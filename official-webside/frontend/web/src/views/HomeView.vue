<template>
  <div class="page-wrapper">
    <!-- =========================================================
         HERO
    ========================================================== -->
    <section class="hero">
      <HeroCarousel :interval="4000"></HeroCarousel>
      <div class="hero-content">

        <div class="hero-kicker">
          SWAN TRACE MINECRAFT COMMUNITY
        </div>

        <h1 class="hero-title">
          <span class="highlight">鸿迹</span>
        </h1>

        <p class="hero-sub">
          鸿迹（Swan Trace）飞鸿踏雪，痕迹不朽。
        </p>

        <p class="hero-description">
          一个公益、自由、纯净，由玩家共同维护的 Minecraft 社区。
          <br />
          希望多年以后，当你再次来到这里，还能找到属于你的那一份痕迹。
        </p>

        <div class="hero-actions">
          <button class="btn-primary btn-join" @click="router.push('/join')">
            加入鸿迹
          </button>

          <a class="btn-ghost" href="https://github.com/LiveLoon/swan-trace-minecraft-community" target="_blank"
            rel="noopener noreferrer">
            查看开源项目
          </a>
        </div>
      </div>

      <div class="hero-scroll">
        <span>向下探索</span>
        <span>↓</span>
      </div>
    </section>

    <!-- =========================================================
         核心理念
    ========================================================== -->
    <section class="section philosophy-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">SWAN TRACE</span>
            <h2>留下你的痕迹</h2>
          </div>

          <span class="badge">
            一个属于玩家的 Minecraft 社区
          </span>
        </div>

        <div class="philosophy-grid">
          <div class="philosophy-card philosophy-card-main">
            <div class="philosophy-number">01</div>

            <h3>让痕迹留下来</h3>

            <p>
              鸿迹希望可以留下你的痕迹。
              当你多年以后再次来到这里，
              这里依然存在你曾经建造、探索和生活过的世界，
              那些记忆可能会让你再次感动。
            </p>
          </div>

          <div class="philosophy-card">
            <div class="philosophy-number">02</div>

            <h3>存档广泛传播</h3>

            <p>
              每一个在本服务器游玩的玩家都可以同步服务器存档。
              你可以保存这个世界，也可以在未来成为新的腐竹，
              去做你认为有趣的事情。
            </p>
          </div>

          <div class="philosophy-card">
            <div class="philosophy-number">03</div>

            <h3>分布式存储</h3>

            <p>
              鸿迹的世界并不只存在于一台服务器上。
              玩家可以保存服务器存档，即使服务器因为攻击或其他原因无法启动，
              仍然可以由保存过存档的玩家重新建立服务器。
            </p>
          </div>
        </div>

        <div class="philosophy-bottom">
          <strong>我们希望：</strong>
          你的痕迹，不会因为一台服务器的停止而消失。
        </div>
      </div>
    </section>

    <!-- =========================================================
         名片
    ========================================================== -->
    <section class="section card-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">SERVER CARD</span>
            <h2>服务器名片</h2>
          </div>

          <span class="badge">Swan Trace · v26.2</span>
        </div>

        <div class="info-grid">
          <div class="info-item">
            <span class="label">服务器名称</span>
            <span class="value">
              <strong>鸿迹</strong>
              <span class="sub-value">Swan Trace Minecraft Community</span>
            </span>
          </div>

          <div class="info-item">
            <span class="label">服务器性质</span>
            <span class="value badge-green">公益 · 免费</span>
          </div>

          <div class="info-item">
            <span class="label">游戏版本</span>
            <span class="value">
              <strong>目前最新 26.2</strong>
              <span class="sub-value">随官方最新版本迭代</span>
            </span>
          </div>

          <div class="info-item">
            <span class="label">服务器类型</span>
            <span class="value highlight-code">纯净</span>
          </div>

          <div class="info-item">
            <span class="label">Java版</span>
            <span class="value badge-blue">支持</span>
          </div>

          <div class="info-item">
            <span class="label">基岩版</span>
            <span class="value badge-blue">支持</span>
          </div>

          <div class="info-item">
            <span class="label">正版验证</span>
            <span class="value badge-green">
              需要正版 Minecraft 账户
            </span>
          </div>

          <div class="info-item">
            <span class="label">在线模式</span>
            <span class="value">开启（正版登录）</span>
          </div>

          <div class="info-item">
            <span class="label">最大同时在线</span>
            <span class="value">
              <strong>20 人</strong>
            </span>
          </div>

          <div class="info-item">
            <span class="label">运行时间</span>
            <span class="value badge-gold">
              24 小时 × 7 天
            </span>
          </div>

          <div class="info-item">
            <span class="label">世界边界</span>
            <span class="value highlight-code">
              50000
            </span>
          </div>

          <div class="info-item">
            <span class="label">服务器地址</span>
            <span class="value">
              <a class="info-link" @click="router.push('/join')">
                进 QQ 群获取
              </a>
            </span>
          </div>

          <div class="info-item">
            <span class="label">QQ群</span>
            <span class="value highlight-code">
              661436985
            </span>

            <span class="hint">
              入群获取最新公告
            </span>
          </div>

          <div class="info-item">
            <span class="label">抖音</span>
            <span class="value highlight-code">
              43650753863
            </span>

            <span class="hint">
              查看服务器日常内容
            </span>
          </div>
        </div>
      </div>
    </section>

    <!-- =========================================================
         基本原则
    ========================================================== -->
    <section class="section principle-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">PRINCIPLES</span>
            <h2>服务器基本原则</h2>
          </div>

          <span class="badge badge-warning">
            基本原则不能通过投票修改
          </span>
        </div>

        <div class="principle-grid">
          <div class="principle-card principle-pure">
            <div class="principle-icon">◇</div>

            <h3>纯净</h3>

            <p>
              服务器端无模组、无商业化。
              保持 Minecraft 世界本身的纯粹体验。
            </p>

            <div class="principle-detail">
              <span>无服务端模组</span>
              <span>无商业化</span>
              <span>纯净游戏</span>
            </div>
          </div>

          <div class="principle-card">
            <div class="principle-icon">∞</div>

            <h3>自由</h3>

            <p>
              建筑、红石、探险……
              腐竹不干涉正常游戏行为，
              只要不影响服务器运行和其他玩家。
            </p>

            <div class="principle-detail">
              <span>建筑自由</span>
              <span>红石自由</span>
              <span>探索自由</span>
            </div>
          </div>

          <div class="principle-card">
            <div class="principle-icon">◷</div>

            <h3>区块暂停</h3>

            <p>
              本服务器不支持常加载区块。
              玩家不在的区块会进入
              <strong>Pause</strong> 状态。
            </p>

            <div class="principle-detail">
              <span>玩家在线 → 区块在线</span>
              <span>玩家离线 → 区块暂停</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- =========================================================
         底线原则
    ========================================================== -->
    <section class="section rules-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">RULES</span>
            <h2>服务器底线原则</h2>
          </div>

          <span class="badge badge-danger">
            底线原则不能通过投票修改
          </span>
        </div>

        <div class="rules-intro">
          <strong>底线只有两件事情：</strong>
          不破坏服务器运行，不影响玩家的游玩体验。
          <br />
          在这个前提下，你可以做你想做的事情。
        </div>

        <div class="rules-list">
          <div class="rule-item">
            <span class="rule-num">1</span>

            <div class="rule-content">
              <h4>禁止恶意破坏服务器运行</h4>

              <p>
                严禁 DDoS 攻击、漏洞利用、刷屏、炸服等任何危害服务器稳定性的行为。
                情况严重者将追究法律责任。
              </p>
            </div>
          </div>

          <div class="rule-item">
            <span class="rule-num">2</span>

            <div class="rule-content">
              <h4>禁止恶意暴露服务器 IP</h4>

              <p>
                禁止将服务器 IP 地址恶意暴露给不在本群的玩家。
                如果因此导致服务器遭受攻击或者恶意操作，将按照破坏服务器运行处理。
              </p>
            </div>
          </div>

          <div class="rule-item">
            <span class="rule-num">3</span>

            <div class="rule-content">
              <h4>尊重他人，友善交流</h4>

              <p>
                包括但不限于群内、游戏内禁止辱骂、歧视、
                政治敏感内容等影响玩家游玩体验的行为。
              </p>
            </div>
          </div>

          <div class="rule-item">
            <span class="rule-num">4</span>

            <div class="rule-content">
              <h4>适度的红石</h4>

              <p>
                鼓励红石创造，但请避免建造超大规模高频红石机械，
                以免影响服务器性能。
              </p>
            </div>
          </div>

          <div class="rule-item">
            <span class="rule-num">5</span>

            <div class="rule-content">
              <h4>不得影响其他玩家游玩</h4>

              <p>
                包括但不限于恶意破坏、恶意侵占、视线阻挡、
                恶意追击玩家等影响其他玩家正常游玩的行为。
              </p>
            </div>
          </div>

          <div class="rule-item">
            <span class="rule-num">6</span>

            <div class="rule-content">
              <h4>挂机（AFK）与实体压迫</h4>

              <p>
                如果挂机导致服务器资源耗尽或卡顿，
                管理员有权直接踢出玩家，或者清除该玩家相关实体和资源。
              </p>

              <p class="rule-note">
                单次加载范围内，如果刷怪塔、养殖场等因为实体大规模存在导致服务器卡顿，
                管理员有权在未通知的情况下直接清除实体和刷怪塔/养殖场，以保证 TPS。
              </p>
            </div>
          </div>

          <div class="rule-item">
            <span class="rule-num">7</span>

            <div class="rule-content">
              <h4>禁止恶意宣传或贬低社区</h4>

              <p>
                禁止在没有任何有效证据的情况下，
                恶意宣传、造谣或者贬低鸿迹（Swan Trace）社区和服务器。
              </p>
            </div>
          </div>
        </div>

        <div class="rules-summary">
          <span class="summary-icon">✓</span>

          <div>
            <strong>总结</strong>

            <p>
              不破坏服务器的运行，不影响玩家的游玩体验。
              你可以做你任何想要做的事情。
            </p>
          </div>
        </div>

        <div class="rules-ban">
          任何违反底线的玩家，将直接移除本服务器和本群。
        </div>
      </div>
    </section>

    <!-- =========================================================
         投票治理
    ========================================================== -->
    <section class="section voting-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">COMMUNITY GOVERNANCE</span>
            <h2>玩家投票治理</h2>
          </div>

          <span class="badge">
            玩家共同维护服务器
          </span>
        </div>

        <div class="voting-intro">
          <h3>鸿迹不是一个由某个人单方面决定一切的服务器。</h3>

          <p>
            在不产生实际费用的情况下，
            服务器配置、规则调整、服务器名称、插件以及管理相关事项，
            可以通过玩家投票进行决定。
          </p>

          <p>
            所有投票都必须满足服务器的
            <strong>基本原则</strong>
            和
            <strong>底线原则</strong>。
          </p>
        </div>

        <div class="vote-grid">
          <div class="vote-card">
            <span class="vote-number">01</span>
            <h4>配置修改</h4>
            <p>服务器配置可以通过投票决定。</p>
          </div>

          <div class="vote-card">
            <span class="vote-number">02</span>
            <h4>插件管理</h4>
            <p>插件的添加、删除、修改可以通过投票决定。</p>
          </div>

          <div class="vote-card">
            <span class="vote-number">03</span>
            <h4>管理员选举</h4>
            <p>管理员由玩家发起投票进行选举。</p>
          </div>

          <div class="vote-card">
            <span class="vote-number">04</span>
            <h4>管理命令</h4>
            <p>
              包括 /op、/deop、/gamemode、/gamerule 等管理操作。
            </p>
          </div>

          <div class="vote-card">
            <span class="vote-number">05</span>
            <h4>存档操作</h4>
            <p>
              包括存档回档、备份恢复、存档重置等。
            </p>
          </div>

          <div class="vote-card">
            <span class="vote-number">06</span>
            <h4>其他事项</h4>
            <p>
              不限于以上内容，只要符合服务器基本原则和底线原则。
            </p>
          </div>
        </div>

        <div class="voting-condition">
          <div class="condition-title">
            谁可以发起和参与投票？
          </div>

          <p>
            在本服所有正常玩家中，
            能够正常进入服务器、后台存在 ID 记录，
            且累计游玩时间达到 <strong>1 小时以上</strong> 的玩家，
            都拥有发起投票和参与投票的权利。
          </p>
        </div>
      </div>
    </section>

    <!-- =========================================================
         举报
    ========================================================== -->
    <section class="section report-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">REPORT</span>
            <h2>举报与执行</h2>
          </div>
        </div>

        <div class="report-grid">
          <div class="report-card">
            <div class="report-icon">◎</div>

            <h3>有效证据</h3>

            <p>
              所有举报必须附带包含
              <strong>坐标（F3界面）</strong>
              和
              <strong>破坏者 ID</strong>
              的截图。
            </p>

            <p class="report-tip">
              最好提供完整录屏，否则可能无法受理。
            </p>
          </div>

          <div class="report-card">
            <div class="report-icon">!</div>

            <h3>漏洞钻空子</h3>

            <p>
              对于规则中没有明确列出，
              但明显利用漏洞“钻空子”，
              并且影响服务器运行或玩家体验的行为，
              同样属于需要处理的行为。
            </p>
          </div>

          <div class="report-card report-action">
            <div class="report-icon">→</div>

            <h3>发现问题</h3>

            <p>
              如果你发现服务器存在违规行为或安全问题，
              可以通过 QQ 群联系服主或管理人员。
            </p>

            <button class="btn-secondary" @click="router.push('/join')">
              联系我们
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- =========================================================
         腐竹 / 管理员
    ========================================================== -->
    <section class="section management-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">MANAGEMENT</span>
            <h2>社区管理</h2>
          </div>

          <span class="badge">
            透明 · 公开 · 投票
          </span>
        </div>

        <div class="management-grid">
          <!-- 腐竹 -->
          <div class="management-card">
            <div class="management-role">
              <span class="role-number">01</span>
              <span>腐竹 · 1人</span>
            </div>

            <h3>腐竹的职责</h3>

            <ul>
              <li>独立承担服务器全部运营成本。</li>
              <li>负责服务器技术维护。</li>
              <li>执行玩家投票通过的决议。</li>
              <li>公开服务器管理信息、存档和配置。</li>
              <li>负责服务器规则和免责协议的编写与更新。</li>
              <li>负责服务器项目目录管理。</li>
            </ul>

            <div class="management-right">
              <strong>权利</strong>

              <p>
                腐竹是服务器最终所有权人，
                持有最高运维权限及物理控制权。
              </p>
            </div>
          </div>

          <!-- 管理员 -->
          <div class="management-card">
            <div class="management-role">
              <span class="role-number">02</span>
              <span>管理员 · 2人</span>
            </div>

            <h3>管理员的职责</h3>

            <ul>
              <li>负责新玩家入群、审核和游玩指导。</li>
              <li>介绍服务器规则。</li>
              <li>执行玩家投票通过的决议。</li>
              <li>负责服务器管理信息公开。</li>
              <li>负责官方抖音账号管理。</li>
            </ul>

            <div class="management-right">
              <strong>管理员产生方式</strong>

              <p>
                玩家发起投票，投票结束后胜出，
                并经腐竹线上面试通过后成为管理员。
              </p>
            </div>
          </div>
        </div>

        <div class="management-warning">
          <h3>管理员不是特权身份</h3>

          <p>
            管理权限意味着更大的责任。
            管理员不得利用权限进行报复、刷物品、私自修改服务器、
            泄露玩家隐私或进行其他影响服务器运行和玩家体验的行为。
          </p>

          <button class="btn-secondary" @click="router.push('/rules')">
            查看完整管理规定
          </button>
        </div>
      </div>
    </section>

    <!-- =========================================================
         维护
    ========================================================== -->
    <section class="section maintenance-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">MAINTENANCE</span>
            <h2>维护与更新</h2>
          </div>
        </div>

        <div class="maintenance-grid">
          <div class="maintenance-item">
            <div class="maintenance-icon">↻</div>

            <div class="maintenance-content">
              <h4>版本更新</h4>

              <p>
                服务器根据需求进行版本更新。
                每次大版本升级前会在群内预告，
                并保留旧版备份供过渡。
              </p>
            </div>
          </div>

          <div class="maintenance-item">
            <div class="maintenance-icon">⚙</div>

            <div class="maintenance-content">
              <h4>重启维护</h4>

              <p>
                若遇异常崩溃或计划维护，
                会在群内提前通知。
              </p>
            </div>
          </div>

          <div class="maintenance-item">
            <div class="maintenance-icon">▣</div>

            <div class="maintenance-content">
              <h4>存档备份</h4>

              <p>
                服务器会进行存档备份，
                同时鼓励玩家同步并保存服务器存档，
                形成分布式存储。
              </p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- =========================================================
         玩家组织
    ========================================================== -->
    <section class="section org-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">PLAYER COMMUNITY</span>
            <h2>玩家组织</h2>
          </div>

          <span class="badge">
            玩家自己建立的世界
          </span>
        </div>

        <div class="org-description">
          <p>
            鸿迹不仅属于服务器，也属于每一个玩家。
            玩家可以建立自己的组织、建筑群、社区和其他有趣的项目。
          </p>

          <p>
            未来这里可以展示玩家组织名称、Logo、
            坐标位置和组织介绍。
          </p>
        </div>

        <div class="org-list">
          <div class="org-empty-state">
            <div class="org-empty-icon">⌂</div>

            <p>暂无玩家组织</p>

            <span>
              成为第一个留下组织痕迹的人
            </span>
          </div>
        </div>

        <div class="org-actions">
          <button class="btn-secondary btn-add-org" @click="router.push('/join')">
            ➕ 添加我的组织
          </button>
        </div>
      </div>
    </section>

    <!-- =========================================================
         FAQ
    ========================================================== -->
    <section class="section faq-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">FAQ</span>
            <h2>常见问题</h2>
          </div>
        </div>

        <div class="faq-list">
          <div class="faq-item">
            <div class="faq-q">
              Q：为什么需要正版？
            </div>

            <div class="faq-a">
              A：保证玩家身份唯一，方便我们追溯违规行为。
            </div>
          </div>

          <div class="faq-item">
            <div class="faq-q">
              Q：服务器卡顿怎么办？
            </div>

            <div class="faq-a">
              A：低成本服务器性能有限，20 人满员时可能略有延迟。
              建议避免高峰期集中加载新区块。
            </div>
          </div>

          <div class="faq-item">
            <div class="faq-q">
              Q：我能当管理员 / OP 吗？
            </div>

            <div class="faq-a">
              A：管理员由玩家发起投票选举，
              并需要经过腐竹线上面试。
              管理权限意味着更大的责任。
              服务器配置和管理命令等事项也可以通过投票决定。
            </div>
          </div>

          <div class="faq-item">
            <div class="faq-q">
              Q：服务器可以使用常加载区块吗？
            </div>

            <div class="faq-a">
              A：不支持常加载区块。
              玩家不在的区块会进入 Pause 状态。
            </div>
          </div>

          <div class="faq-item">
            <div class="faq-q">
              Q：世界边界是多少？
            </div>

            <div class="faq-a">
              A：目前世界边界限制为 50000。
            </div>
          </div>

          <div class="faq-item">
            <div class="faq-q">
              Q：世界和存档会重置吗？
            </div>

            <div class="faq-a">
              A：目前不会轻易重置。
              如果未来因为版本大更新等原因需要重置，
              会提前提醒玩家。
            </div>
          </div>

          <div class="faq-item">
            <div class="faq-q">
              Q：服务器存档安全吗？
            </div>

            <div class="faq-a">
              A：鸿迹鼓励玩家同步和保存服务器存档，
              通过玩家之间的存档分布式存储，
              降低服务器单点故障导致世界消失的风险。
            </div>
          </div>

          <div class="faq-item">
            <div class="faq-q">
              Q：服务器地址在哪里？
            </div>

            <div class="faq-a">
              A：服务器地址通过 QQ 群 661436985 获取。
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- =========================================================
         项目
    ========================================================== -->
    <section class="section project-section">
      <div class="container">
        <div class="section-header">
          <div>
            <span class="section-eyebrow">OPEN SOURCE</span>
            <h2>项目与开源</h2>
          </div>

          <span class="badge">
            社区共同维护
          </span>
        </div>

        <div class="project-grid">
          <div class="project-card">
            <div class="project-icon">⌘</div>

            <h3>鸿迹服务器项目</h3>

            <p>
              服务器配置、脚本以及相关项目公开维护。
            </p>

            <a href="https://github.com/LiveLoon/swan-trace-minecraft-community" target="_blank"
              rel="noopener noreferrer" class="project-link">
              GitHub →
            </a>
          </div>

          <div class="project-card">
            <div class="project-icon">⇄</div>

            <h3>服务器存档同步</h3>

            <p>
              使用 WorldSync 工具同步服务器世界存档，
              让玩家可以保存属于鸿迹的世界。
            </p>

            <a href="https://github.com/LiveLoon/world-sync-mc-paper-plugin" target="_blank" rel="noopener noreferrer"
              class="project-link">
              WorldSync →
            </a>
          </div>
        </div>
      </div>
    </section>

    <!-- =========================================================
         加入我们
    ========================================================== -->
    <section class="section join-section">
      <div class="container">
        <div class="join-card">
          <span class="section-eyebrow">JOIN SWAN TRACE</span>

          <h2>加入鸿迹</h2>

          <p>
            如果你也希望在 Minecraft 世界里留下属于自己的痕迹，
            欢迎来到鸿迹。
          </p>

          <div class="join-steps">
            <div class="join-step">
              <span>01</span>
              <strong>加入 QQ 群</strong>
              <p>QQ群：661436985</p>
            </div>

            <div class="join-step">
              <span>02</span>
              <strong>验证正版</strong>
              <p>使用正版 Minecraft 账户登录</p>
            </div>

            <div class="join-step">
              <span>03</span>
              <strong>加入服务器</strong>
              <p>服务器地址在 QQ 群内获取</p>
            </div>
          </div>

          <button class="btn-primary" @click="router.push('/join')">
            查看加入方式
          </button>
        </div>
      </div>
    </section>
    <FooterC />
  </div>
</template>

<script setup lang="ts">
import FooterC from '@/components/FooterC.vue'
import HeroCarousel from '@/components/HeroCarousel.vue'
import router from '@/router'
import { onMounted, onUnmounted, ref } from 'vue'

let observer: IntersectionObserver | null = null

onMounted(() => {

  /**
   * 页面滚动进入动画
   */
  const sections = document.querySelectorAll(
    '.section:not(.hero)',
  )

  observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add('is-visible')
          observer?.unobserve(entry.target)
        }
      })
    },
    {
      threshold: 0.1,
      rootMargin: '0px 0px -50px 0px',
    },
  )

  sections.forEach((section) => observer?.observe(section))
})

onUnmounted(() => {
  observer?.disconnect()
})
</script>

<style scoped>
/* =========================================================
   RESET
========================================================= */

* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

.page-wrapper {
  min-height: 100vh;
  font-family:
    'Segoe UI',
    'PingFang SC',
    'Microsoft YaHei',
    Roboto,
    system-ui,
    -apple-system,
    sans-serif;

  background:
    radial-gradient(circle at 20% 0%,
      rgba(90, 150, 210, 0.08),
      transparent 32%),
    #0b0e13;

  color: #e8edf2;
  line-height: 1.7;
}

.container {
  width: 100%;
  max-width: 1120px;
  margin: 0 auto;
  padding: 0 24px;
}

.section {
  padding: 82px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.045);

  opacity: 0;
  transform: translateY(30px);

  transition:
    opacity 0.8s ease,
    transform 0.8s ease;
}

.section.is-visible {
  opacity: 1;
  transform: translateY(0);
}

.hero {
  opacity: 1 !important;
  transform: none !important;
}

.section-header {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  flex-wrap: wrap;

  gap: 16px;

  margin-bottom: 36px;
}

.section-eyebrow {
  display: block;

  margin-bottom: 7px;

  color: #79baf1;

  font-size: 11px;
  font-weight: 700;

  letter-spacing: 2.8px;
  text-transform: uppercase;
}

.section-header h2 {
  font-size: 30px;
  font-weight: 700;
  letter-spacing: -0.5px;

  background: linear-gradient(135deg,
      #f4f0e7,
      #b8d7eb);

  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.badge {
  display: inline-flex;
  align-items: center;

  min-height: 30px;

  padding: 5px 14px;

  border-radius: 999px;

  border: 1px solid rgba(150, 190, 220, 0.14);

  background: rgba(150, 190, 220, 0.08);

  color: #abc6da;

  font-size: 12px;
  font-weight: 500;
}

.badge-warning {
  color: #e3c789;
  background: rgba(220, 180, 80, 0.08);
  border-color: rgba(220, 180, 80, 0.15);
}

.badge-danger {
  color: #e7a6a6;
  background: rgba(220, 80, 80, 0.08);
  border-color: rgba(220, 80, 80, 0.15);
}

.badge-green {
  display: inline-flex;

  padding: 3px 13px;

  border-radius: 999px;

  background: rgba(80, 200, 120, 0.1);

  color: #7ddfa5;

  border: 1px solid rgba(80, 200, 120, 0.15);

  font-size: 14px;
}

.badge-blue {
  display: inline-flex;

  padding: 3px 13px;

  border-radius: 999px;

  background: rgba(100, 170, 240, 0.1);

  color: #8fc9f2;

  border: 1px solid rgba(100, 170, 240, 0.15);

  font-size: 14px;
}

.badge-gold {
  display: inline-flex;

  padding: 3px 13px;

  border-radius: 999px;

  background: rgba(255, 200, 80, 0.08);

  color: #f0d48e;

  border: 1px solid rgba(255, 200, 80, 0.13);

  font-size: 14px;
}

strong {
  color: #f1e7d2;
}

/* =========================================================
   HERO
========================================================= */

.hero {
  position: relative;

  min-height: 650px;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 110px 24px 80px;

  overflow: hidden;

  background: #080b0f;

  text-align: center;
}

.hero-content {
  position: relative;

  z-index: 2;

  max-width: 760px;
}

.hero-kicker {
  margin-bottom: 8px;

  color: rgba(205, 225, 240, 0.72);

  font-size: 11px;
  font-weight: 700;

  letter-spacing: 3px;
}

.hero-title {
  margin-bottom: 12px;

  font-size: 84px;
  font-weight: 800;

  line-height: 1;

  letter-spacing: -3px;

  text-shadow:
    0 4px 30px rgba(0, 0, 0, 0.65);
}

.hero-title .highlight {
  color: #b8dcf5;

  text-shadow:
    0 0 45px rgba(110, 185, 240, 0.28);
}

.hero-sub {
  max-width: 620px;

  margin: 0 auto 14px;

  color: #f0f5f9;

  font-size: 21px;
  font-weight: 350;

  text-shadow:
    0 2px 12px rgba(0, 0, 0, 0.8);
}

.hero-description {
  max-width: 650px;

  margin: 0 auto 32px;

  color: rgba(222, 234, 243, 0.82);

  font-size: 15px;

  line-height: 1.9;

  text-shadow:
    0 2px 12px rgba(0, 0, 0, 0.75);
}

.hero-actions {
  display: flex;
  justify-content: center;
  align-items: center;

  gap: 12px;

  flex-wrap: wrap;
}

.btn-primary,
.btn-secondary,
.btn-ghost {
  display: inline-flex;
  align-items: center;
  justify-content: center;

  cursor: pointer;

  text-decoration: none;

  transition:
    transform 0.25s ease,
    box-shadow 0.25s ease,
    background 0.25s ease,
    border-color 0.25s ease;
}

.btn-primary {
  border: none;

  padding: 13px 38px;

  border-radius: 999px;

  background:
    linear-gradient(135deg,
      #73b7ec,
      #477fae);

  color: #fff;

  font-size: 16px;
  font-weight: 650;

  box-shadow:
    0 10px 35px rgba(80, 155, 220, 0.25);
}

.btn-primary:hover {
  transform: translateY(-3px);

  box-shadow:
    0 15px 40px rgba(80, 155, 220, 0.4);
}

.btn-ghost {
  padding: 12px 28px;

  border-radius: 999px;

  border: 1px solid rgba(220, 235, 245, 0.2);

  background: rgba(255, 255, 255, 0.04);

  color: #d9e7f1;

  font-size: 15px;
}

.btn-ghost:hover {
  transform: translateY(-2px);

  background: rgba(255, 255, 255, 0.08);

  border-color: rgba(220, 235, 245, 0.35);
}

.hero-scroll {
  position: absolute;

  bottom: 22px;
  left: 50%;

  transform: translateX(-50%);

  display: flex;
  flex-direction: column;
  gap: 2px;

  color: rgba(215, 230, 240, 0.65);

  font-size: 12px;

  z-index: 2;

  animation: bounceDown 2s infinite;
}

.hero-scroll span:last-child {
  font-size: 20px;
}

@keyframes bounceDown {

  0%,
  100% {
    transform: translateX(-50%) translateY(0);
  }

  50% {
    transform: translateX(-50%) translateY(8px);
  }
}

/* =========================================================
   PHILOSOPHY
========================================================= */

.philosophy-grid {
  display: grid;

  grid-template-columns:
    1.2fr 1fr 1fr;

  gap: 20px;
}

.philosophy-card {
  padding: 30px;

  border-radius: 22px;

  background: rgba(255, 255, 255, 0.025);

  border: 1px solid rgba(255, 255, 255, 0.055);

  transition:
    transform 0.25s ease,
    background 0.25s ease,
    border-color 0.25s ease;
}

.philosophy-card:hover {
  transform: translateY(-5px);

  background: rgba(255, 255, 255, 0.045);

  border-color: rgba(110, 180, 235, 0.18);
}

.philosophy-card-main {
  background:
    linear-gradient(145deg,
      rgba(100, 170, 220, 0.08),
      rgba(255, 255, 255, 0.025));
}

.philosophy-number {
  margin-bottom: 18px;

  color: #73b6e9;

  font-family: 'JetBrains Mono', monospace;

  font-size: 12px;
  font-weight: 700;

  letter-spacing: 1px;
}

.philosophy-card h3 {
  margin-bottom: 10px;

  color: #e6edf3;

  font-size: 21px;
}

.philosophy-card p {
  color: #aebdca;

  font-size: 15px;
}

.philosophy-bottom {
  margin-top: 22px;

  padding: 18px 24px;

  border-left: 3px solid rgba(110, 180, 235, 0.4);

  border-radius: 0 14px 14px 0;

  background: rgba(110, 180, 235, 0.045);

  color: #b7cbd9;

  font-size: 15px;
}

/* =========================================================
   SERVER CARD
========================================================= */

.info-grid {
  display: grid;

  grid-template-columns: repeat(2, 1fr);

  gap: 0 30px;

  padding: 28px 30px;

  border-radius: 22px;

  background: rgba(255, 255, 255, 0.025);

  border: 1px solid rgba(255, 255, 255, 0.06);
}

.info-item {
  display: flex;

  align-items: baseline;

  flex-wrap: wrap;

  gap: 6px 12px;

  min-height: 56px;

  padding: 13px 0;

  border-bottom: 1px solid rgba(255, 255, 255, 0.045);
}

.info-item:nth-last-child(-n + 2) {
  border-bottom: none;
}

.info-item .label {
  min-width: 110px;

  color: #7f97aa;

  font-size: 13px;
  font-weight: 500;
}

.info-item .value {
  color: #dce6ee;

  font-size: 14px;
}

.sub-value {
  margin-left: 7px;

  color: #8298a9;

  font-size: 12px;
}

.highlight-code {
  display: inline-flex;

  padding: 3px 11px;

  border-radius: 999px;

  background: rgba(100, 175, 235, 0.08);

  border: 1px solid rgba(100, 175, 235, 0.11);

  color: #8cc9f3;

  font-family:
    'JetBrains Mono',
    'Fira Code',
    monospace;

  font-size: 12px;
}

.hint {
  color: #697e8f;

  font-size: 12px;
}

.info-link {
  color: #8fc9f2;

  cursor: pointer;

  text-decoration: underline;

  text-underline-offset: 3px;
}

/* =========================================================
   PRINCIPLES
========================================================= */

.principle-grid {
  display: grid;

  grid-template-columns: repeat(3, 1fr);

  gap: 20px;
}

.principle-card {
  padding: 32px;

  border-radius: 22px;

  background: rgba(255, 255, 255, 0.025);

  border: 1px solid rgba(255, 255, 255, 0.055);

  transition: all 0.25s ease;
}

.principle-card:hover {
  transform: translateY(-5px);

  background: rgba(255, 255, 255, 0.045);

  border-color: rgba(110, 180, 235, 0.2);
}

.principle-icon {
  width: 50px;
  height: 50px;

  display: flex;
  align-items: center;
  justify-content: center;

  margin-bottom: 20px;

  border-radius: 15px;

  background: rgba(100, 175, 235, 0.08);

  color: #83c2ed;

  font-size: 25px;
}

.principle-card h3 {
  margin-bottom: 9px;

  color: #e2ebf2;

  font-size: 22px;
}

.principle-card p {
  min-height: 74px;

  color: #aebdca;

  font-size: 15px;
}

.principle-detail {
  display: flex;
  flex-wrap: wrap;

  gap: 7px;

  margin-top: 22px;
}

.principle-detail span {
  padding: 4px 9px;

  border-radius: 8px;

  background: rgba(255, 255, 255, 0.035);

  color: #8197a8;

  font-size: 11px;
}

/* =========================================================
   RULES
========================================================= */

.rules-intro {
  margin-bottom: 24px;

  padding: 20px 24px;

  border-radius: 16px;

  background: rgba(255, 200, 100, 0.045);

  border: 1px solid rgba(255, 200, 100, 0.08);

  color: #b9c4cd;

  font-size: 14px;
}

.rules-intro strong {
  color: #ecd9a6;
}

.rules-list {
  display: flex;

  flex-direction: column;

  gap: 14px;
}

.rule-item {
  display: flex;

  gap: 17px;

  padding: 20px 22px;

  border-radius: 17px;

  border-left: 3px solid rgba(110, 180, 235, 0.27);

  background: rgba(255, 255, 255, 0.022);

  transition:
    background 0.2s ease,
    transform 0.2s ease;
}

.rule-item:hover {
  background: rgba(255, 255, 255, 0.04);

  transform: translateX(3px);
}

.rule-num {
  flex-shrink: 0;

  width: 34px;
  height: 34px;

  display: flex;
  align-items: center;
  justify-content: center;

  border-radius: 50%;

  background: rgba(100, 175, 235, 0.1);

  color: #7fc0ed;

  font-size: 14px;
  font-weight: 700;
}

.rule-content h4 {
  margin-bottom: 4px;

  color: #e1e9ef;

  font-size: 17px;
}

.rule-content p {
  color: #aabcca;

  font-size: 14px;
}

.rule-note {
  margin-top: 9px;

  padding: 11px 15px;

  border-left: 2px solid rgba(240, 190, 80, 0.28);

  border-radius: 9px;

  background: rgba(240, 190, 80, 0.045);

  color: #cbbd9e !important;

  font-size: 13px !important;
}

.rules-summary {
  display: flex;

  gap: 14px;

  align-items: center;

  margin-top: 28px;

  padding: 20px 24px;

  border-radius: 18px;

  background: rgba(90, 190, 125, 0.045);

  border: 1px solid rgba(90, 190, 125, 0.1);
}

.summary-icon {
  width: 38px;
  height: 38px;

  display: flex;
  align-items: center;
  justify-content: center;

  flex-shrink: 0;

  border-radius: 50%;

  background: rgba(90, 190, 125, 0.1);

  color: #79dca0;

  font-weight: 700;
}

.rules-summary strong {
  display: block;

  margin-bottom: 2px;

  color: #9ae1b4;
}

.rules-summary p {
  color: #a7b9b1;

  font-size: 14px;
}

.rules-ban {
  margin-top: 15px;

  text-align: center;

  color: #d99999;

  font-size: 13px;
}

/* =========================================================
   VOTING
========================================================= */

.voting-intro {
  max-width: 850px;

  margin-bottom: 28px;
}

.voting-intro h3 {
  margin-bottom: 9px;

  color: #e5edf3;

  font-size: 22px;
}

.voting-intro p {
  color: #aebdca;

  font-size: 15px;
}

.vote-grid {
  display: grid;

  grid-template-columns: repeat(3, 1fr);

  gap: 16px;
}

.vote-card {
  padding: 24px;

  border-radius: 18px;

  background: rgba(255, 255, 255, 0.025);

  border: 1px solid rgba(255, 255, 255, 0.05);
}

.vote-number {
  color: #70b5e8;

  font-family: monospace;

  font-size: 11px;
}

.vote-card h4 {
  margin: 12px 0 5px;

  color: #dce7ee;

  font-size: 17px;
}

.vote-card p {
  color: #9fb2c0;

  font-size: 13px;
}

.voting-condition {
  margin-top: 20px;

  padding: 22px 25px;

  border-radius: 18px;

  background:
    linear-gradient(135deg,
      rgba(100, 170, 225, 0.08),
      rgba(255, 255, 255, 0.02));

  border: 1px solid rgba(100, 170, 225, 0.12);
}

.condition-title {
  margin-bottom: 5px;

  color: #d8e8f3;

  font-size: 16px;
  font-weight: 650;
}

.voting-condition p {
  color: #aebdca;

  font-size: 14px;
}

/* =========================================================
   REPORT
========================================================= */

.report-grid {
  display: grid;

  grid-template-columns: repeat(3, 1fr);

  gap: 20px;
}

.report-card {
  padding: 30px;

  border-radius: 20px;

  background: rgba(255, 255, 255, 0.025);

  border: 1px solid rgba(255, 255, 255, 0.055);
}

.report-icon {
  width: 48px;
  height: 48px;

  display: flex;
  align-items: center;
  justify-content: center;

  margin-bottom: 18px;

  border-radius: 14px;

  background: rgba(100, 175, 235, 0.08);

  color: #83c2ed;

  font-size: 22px;
  font-weight: 700;
}

.report-card h3 {
  margin-bottom: 9px;

  color: #e0e8ee;

  font-size: 20px;
}

.report-card p {
  color: #a9bac7;

  font-size: 14px;
}

.report-tip {
  margin-top: 12px;

  color: #c6ae78 !important;
}

.report-action {
  display: flex;

  flex-direction: column;

  align-items: flex-start;
}

.report-action .btn-secondary {
  margin-top: 20px;
}

/* =========================================================
   MANAGEMENT
========================================================= */

.management-grid {
  display: grid;

  grid-template-columns: repeat(2, 1fr);

  gap: 20px;
}

.management-card {
  padding: 30px;

  border-radius: 22px;

  background: rgba(255, 255, 255, 0.025);

  border: 1px solid rgba(255, 255, 255, 0.055);
}

.management-role {
  display: flex;
  align-items: center;

  gap: 10px;

  margin-bottom: 17px;

  color: #9fc9e7;

  font-size: 13px;
  font-weight: 650;
}

.role-number {
  color: #6eafe0;

  font-family: monospace;
}

.management-card h3 {
  margin-bottom: 12px;

  color: #e0e8ef;

  font-size: 20px;
}

.management-card ul {
  padding-left: 20px;
}

.management-card li {
  margin-bottom: 7px;

  color: #aabcca;

  font-size: 14px;
}

.management-right {
  margin-top: 20px;

  padding: 15px 17px;

  border-radius: 13px;

  background: rgba(100, 175, 235, 0.045);
}

.management-right strong {
  display: block;

  margin-bottom: 4px;

  color: #9fcbea;

  font-size: 13px;
}

.management-right p {
  color: #9eb1bf;

  font-size: 13px;
}

.management-warning {
  margin-top: 20px;

  padding: 24px;

  border-radius: 18px;

  background: rgba(230, 180, 70, 0.045);

  border: 1px solid rgba(230, 180, 70, 0.1);
}

.management-warning h3 {
  margin-bottom: 5px;

  color: #e1d1a4;

  font-size: 17px;
}

.management-warning p {
  margin-bottom: 15px;

  color: #adad9f;

  font-size: 14px;
}

/* =========================================================
   BUTTON
========================================================= */

.btn-secondary {
  padding: 9px 23px;

  border-radius: 999px;

  border: 1px solid rgba(170, 200, 220, 0.2);

  background: transparent;

  color: #c5d9e7;

  font-size: 14px;

  cursor: pointer;
}

.btn-secondary:hover {
  transform: translateY(-2px);

  background: rgba(100, 175, 235, 0.08);

  border-color: rgba(100, 175, 235, 0.35);

  color: #fff;
}

/* =========================================================
   MAINTENANCE
========================================================= */

.maintenance-grid {
  display: grid;

  grid-template-columns: repeat(3, 1fr);

  gap: 18px;
}

.maintenance-item {
  display: flex;

  gap: 16px;

  padding: 23px;

  border-radius: 17px;

  background: rgba(255, 255, 255, 0.022);

  border: 1px solid rgba(255, 255, 255, 0.045);
}

.maintenance-icon {
  width: 40px;
  height: 40px;

  display: flex;
  align-items: center;
  justify-content: center;

  flex-shrink: 0;

  border-radius: 12px;

  background: rgba(100, 175, 235, 0.08);

  color: #83c2ed;
}

.maintenance-content h4 {
  margin-bottom: 5px;

  color: #dbe5eb;

  font-size: 16px;
}

.maintenance-content p {
  color: #9fb1bf;

  font-size: 13px;
}

/* =========================================================
   PLAYER ORGANIZATIONS
========================================================= */

.org-description {
  max-width: 850px;

  margin-bottom: 24px;
}

.org-description p {
  margin-bottom: 5px;

  color: #a9bac7;

  font-size: 14px;
}

.org-list {
  min-height: 190px;

  display: flex;
  align-items: center;
  justify-content: center;

  padding: 35px 20px;

  border-radius: 22px;

  background: rgba(255, 255, 255, 0.02);

  border: 1px solid rgba(255, 255, 255, 0.055);
}

.org-empty-state {
  text-align: center;
}

.org-empty-icon {
  margin-bottom: 7px;

  color: #7693a7;

  font-size: 38px;
}

.org-empty-state p {
  color: #aebdca;

  font-size: 17px;
  font-weight: 600;
}

.org-empty-state span {
  color: #718797;

  font-size: 13px;
}

.org-actions {
  margin-top: 20px;

  text-align: center;
}

/* =========================================================
   FAQ
========================================================= */

.faq-list {
  display: flex;

  flex-direction: column;

  gap: 12px;
}

.faq-item {
  padding: 18px 22px;

  border-radius: 15px;

  border-left: 3px solid rgba(120, 180, 220, 0.16);

  background: rgba(255, 255, 255, 0.022);
}

.faq-q {
  margin-bottom: 4px;

  color: #dce6ed;

  font-size: 15px;
  font-weight: 650;
}

.faq-a {
  color: #a7b9c6;

  font-size: 14px;
}

/* =========================================================
   PROJECT
========================================================= */

.project-grid {
  display: grid;

  grid-template-columns: repeat(2, 1fr);

  gap: 20px;
}

.project-card {
  padding: 30px;

  border-radius: 20px;

  background: rgba(255, 255, 255, 0.025);

  border: 1px solid rgba(255, 255, 255, 0.055);
}

.project-icon {
  width: 46px;
  height: 46px;

  display: flex;
  align-items: center;
  justify-content: center;

  margin-bottom: 17px;

  border-radius: 13px;

  background: rgba(100, 175, 235, 0.08);

  color: #82c0eb;

  font-size: 21px;
}

.project-card h3 {
  margin-bottom: 8px;

  color: #e0e8ef;

  font-size: 19px;
}

.project-card p {
  margin-bottom: 17px;

  color: #a5b6c3;

  font-size: 14px;
}

.project-link {
  color: #86c4ee;

  font-size: 14px;

  text-decoration: none;
}

.project-link:hover {
  text-decoration: underline;
}

/* =========================================================
   JOIN
========================================================= */

.join-card {
  padding: 55px 40px;

  text-align: center;

  border-radius: 28px;

  background:
    radial-gradient(circle at 50% 0%,
      rgba(110, 180, 235, 0.1),
      transparent 55%),
    rgba(255, 255, 255, 0.025);

  border: 1px solid rgba(110, 180, 235, 0.12);
}

.join-card h2 {
  margin-bottom: 9px;

  color: #eaf1f5;

  font-size: 32px;
}

.join-card>p {
  max-width: 650px;

  margin: 0 auto;

  color: #a9bac7;

  font-size: 15px;
}

.join-steps {
  display: grid;

  grid-template-columns: repeat(3, 1fr);

  gap: 15px;

  max-width: 800px;

  margin: 32px auto;
}

.join-step {
  padding: 18px;

  border-radius: 15px;

  background: rgba(255, 255, 255, 0.025);
}

.join-step>span {
  display: block;

  margin-bottom: 6px;

  color: #73b8e9;

  font-family: monospace;

  font-size: 11px;
}

.join-step strong {
  display: block;

  margin-bottom: 3px;

  color: #dce7ee;

  font-size: 15px;
}

.join-step p {
  color: #8499a9;

  font-size: 12px;
}

/* =========================================================
   RESPONSIVE
========================================================= */

@media (max-width: 900px) {

  .philosophy-grid,
  .principle-grid,
  .vote-grid,
  .report-grid,
  .maintenance-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .philosophy-card-main {
    grid-column: 1 / -1;
  }

  .info-grid {
    grid-template-columns: 1fr;
  }

  .info-item:nth-last-child(-n + 2) {
    border-bottom: 1px solid rgba(255, 255, 255, 0.045);
  }

  .info-item:last-child {
    border-bottom: none;
  }

  .management-grid,
  .project-grid {
    grid-template-columns: 1fr;
  }

  .join-steps {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .section {
    padding: 60px 0;
  }

  .hero {
    min-height: 600px;

    padding: 90px 20px 70px;
  }

  .hero-title {
    font-size: 62px;

    letter-spacing: -2px;
  }

  .hero-sub {
    font-size: 18px;
  }

  .hero-description {
    font-size: 14px;
  }

  .section-header h2 {
    font-size: 25px;
  }

  .philosophy-grid,
  .principle-grid,
  .vote-grid,
  .report-grid,
  .maintenance-grid {
    grid-template-columns: 1fr;
  }

  .philosophy-card-main {
    grid-column: auto;
  }

  .principle-card p {
    min-height: auto;
  }

  .rule-item {
    align-items: flex-start;
  }

  .join-card {
    padding: 40px 22px;
  }
}

@media (max-width: 480px) {
  .container {
    padding: 0 16px;
  }

  .hero {
    min-height: 550px;

    padding: 75px 16px 55px;
  }

  .hero-kicker {
    font-size: 9px;

    letter-spacing: 2px;
  }

  .hero-title {
    font-size: 50px;
  }

  .hero-sub {
    font-size: 16px;
  }

  .hero-description {
    font-size: 13px;
  }

  .hero-actions {
    flex-direction: column;
  }

  .btn-primary,
  .btn-ghost {
    width: 100%;
    max-width: 260px;
  }

  .section {
    padding: 48px 0;
  }

  .section-header {
    align-items: flex-start;
  }

  .section-header h2 {
    font-size: 23px;
  }

  .info-grid {
    padding: 20px;
  }

  .info-item {
    flex-direction: column;

    align-items: flex-start;

    gap: 4px;
  }

  .info-item .label {
    min-width: auto;
  }

  .rule-item {
    flex-direction: column;

    gap: 10px;

    padding: 17px;
  }

  .rules-intro {
    padding: 17px;
  }

  .management-card,
  .project-card,
  .report-card,
  .principle-card {
    padding: 24px;
  }

  .join-card h2 {
    font-size: 27px;
  }

  .join-card>p {
    font-size: 14px;
  }
}
</style>